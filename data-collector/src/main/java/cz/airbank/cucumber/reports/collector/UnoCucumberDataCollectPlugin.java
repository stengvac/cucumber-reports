package cz.airbank.cucumber.reports.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.airbank.cucumber.reports.transport.model.BuildRunMetadata;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.TestSuiteMetadata;

import cz.airbank.cucumber.reports.transport.model.validation.ErrorListTo;
import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.ScenarioOutline;

/**
 * Uno cucumber data collect plugin.
 * This class is used to collect data for class annotated with
 * {@link org.junit.runner.RunWith} and {@link UnoCucumberRunner}.
 * Plugin will send collected data after every processed feature file to REST API.
 * Internally this class create data collector {@link UnoCucumberDataCollector} for each processed feature file.
 *
 * @author Vaclav Stengl
 */
//TODO create test for this class
public class UnoCucumberDataCollectPlugin implements Formatter, Reporter {

    private static final Logger sLog = LoggerFactory.getLogger(UnoCucumberDataCollectPlugin.class);

    private final String restCollectDataUrl;
    private final long sequentialNumber;
    private final String projectName;
    private final String glue;
    private final ObjectMapper mapper;

    /**
     * For every processed feature new instance of data collector is created.
     */
    private UnoCucumberDataCollector dataCollector;

    public UnoCucumberDataCollectPlugin(String sequentialNumber, String projectName, String restCollectDataUrl, String glue) {
        this.projectName = projectName;
        this.sequentialNumber = Long.valueOf(sequentialNumber);
        this.restCollectDataUrl = restCollectDataUrl;
        this.glue = glue;

        mapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(timeModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
        dataCollector.syntaxError(state, event, legalEvents, uri, line);
    }

    @Override
    public void uri(String s) {
        //first method called for .feature so its used as signal "new feature tested"
        //each processed feature deserve new data collector bcs data collector is stateful
        dataCollector = new UnoCucumberDataCollector(glue);
        dataCollector.uri(s);
    }

    @Override
    public void feature(gherkin.formatter.model.Feature feature) {
        dataCollector.feature(feature);
    }

    @Override
    public void background(Background background) {
        dataCollector.background(background);
    }

    @Override
    public void scenario(gherkin.formatter.model.Scenario scenario) {
        dataCollector.scenario(scenario);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        dataCollector.scenarioOutline(scenarioOutline);
    }

    @Override
    public void examples(Examples examples) {
        dataCollector.examples(examples);
    }

    @Override
    public void startOfScenarioLifeCycle(gherkin.formatter.model.Scenario scenario) {
        dataCollector.startOfScenarioLifeCycle(scenario);
    }

    @Override
    public void step(gherkin.formatter.model.Step step) {
        dataCollector.step(step);
    }

    @Override
    public void endOfScenarioLifeCycle(gherkin.formatter.model.Scenario scenario) {
        dataCollector.endOfScenarioLifeCycle(scenario);
    }

    @Override
    public void done() {
        dataCollector.done();
    }

    @Override
    public void before(Match match, Result result) {
        dataCollector.before(match, result);
    }

    @Override
    public void result(Result result) {
        dataCollector.result(result);
    }

    @Override
    public void after(Match match, Result result) {
        dataCollector.after(match, result);
    }

    @Override
    public void match(Match match) {
        dataCollector.match(match);
    }

    @Override
    public void embedding(String mineType, byte[] bytes) {
        dataCollector.embedding(mineType, bytes);
    }

    @Override
    public void write(String text) {
        dataCollector.write(text);
    }

    @Override
    public void close() {
        dataCollector.close();
    }

    @Override
    public void eof() {
        //feature testing complete -> time to write results to json and post
        dataCollector.eof();

        BuildRunMetadata metadata = new BuildRunMetadata();
        metadata.setProjectName(projectName);
        metadata.setSequentialNumber(sequentialNumber);
        metadata.setBuildAt(LocalDateTime.now());

        HttpURLConnection conn = null;
        Feature testedFeature = dataCollector.getTestedFeature();
        //TODO decide information and their sources which should be passed to test suite metadata - spring profile, etc.
        testedFeature.setTestSuiteMetadata(new TestSuiteMetadata());
        testedFeature.setBuildRunMetadata(metadata);

        try {
            conn = (HttpURLConnection) new URL(restCollectDataUrl).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            mapper.writeValue(conn.getOutputStream(), testedFeature);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                InputStream errorStream = conn.getErrorStream();
                List<ErrorTo> errorToList = Collections.emptyList();
                if (errorStream != null) {
                    ErrorListTo errorListTo = mapper.readValue(errorStream, ErrorListTo.class);
                    if (errorListTo != null) {
                        errorToList = errorListTo.getErrorTos();
                    }
                }

                String msg = MessageFormat.format(
                        "Feature report for feature file {0} was not accepted by REST API module." +
                                "Build : {1}-{2}. Response code: {3}. Errors: {4}",
                        getFullyQualifiedFeatureName(testedFeature),
                        projectName,
                        sequentialNumber,
                        conn.getResponseCode(),
                        formatErrorList(errorToList)
                );

                sLog.error(msg);
            } else {
                String message = MessageFormat.format(
                        "Feature report for feature file {0} was generated. " +
                                "Contains {1} scenario runs. " +
                                "REST API URL {2}.",
                        getFullyQualifiedFeatureName(testedFeature),
                        computeScenarioRunCount(testedFeature),
                        restCollectDataUrl
                );

                sLog.info(message);
            }

        } catch (IOException e) {
            sLog.error("Cucumber report was not successfully generated", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Get full path to feature file. Eq to value provided by uri method.
     *
     * @param feature to get path from
     * @return path to feature file
     */
    private String getFullyQualifiedFeatureName(Feature feature) {
        String module = feature.getFeatureMetadata().getModule();

        if (module == null || "".equals(module)) {
            return feature.getFeatureMetadata().getFilename();
        }

        return module + UnoCucumberDataCollector.DIRECTORY_SEPARATOR + feature.getFeatureMetadata().getFilename();
    }

    /**
     * Compute total count of scenario runs executed during testing of 1 feature file.
     *
     * @param feature to compute scenario executions
     * @return scenario runs count
     */
    private int computeScenarioRunCount(Feature feature) {
        return feature.getScenarioDefinitionList().stream().mapToInt(
                scenarioDefinition -> scenarioDefinition.getScenarioRunList().size()
        ).sum();
    }

    /**
     * Create formatted representation of errors for REST API.
     *
     * @param errorToList to format
     * @return formatted string
     */
    private String formatErrorList(List<ErrorTo> errorToList) {
        String formattedErrors = errorToList.stream().map(
                ErrorTo::toString
        ).collect(Collectors.joining(", "));

        return String.format("[%s]", formattedErrors);
    }
}
