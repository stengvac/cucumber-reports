package cz.airbank.cucumber.reports.collector;

import java.io.IOException;
import java.util.Properties;

import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;

/**
 * Modified {@link cucumber.api.junit.Cucumber} runner.
 * This runner add as plugin {@link UnoCucumberDataCollectPlugin} when conditions are met.
 */
public class UnoCucumberRunner extends Cucumber {

    //TODO load those constants from config
    /**
     * System property with actual job name.
     */
    public static final String JOB_NAME = "cucumber.job.name";
    /**
     * System property with actual job number.
     */
    public static final String JOB_NUMBER = "cucumber.job.sequential.number";
    /**
     * System property with url to collect data.
     */
    public static final String COLLECT_URL = "cucumber.rest.api.url";

    public UnoCucumberRunner(Class clazz) throws InitializationError, IOException {
        super(clazz);
    }

    @Override
    protected Runtime createRuntime(ResourceLoader resourceLoader,
                                    ClassLoader classLoader,
                                    RuntimeOptions runtimeOptions) throws InitializationError, IOException {

        Properties properties = System.getProperties();
        if (properties.containsKey(COLLECT_URL)) {
            UnoCucumberDataCollectPlugin formatter = new UnoCucumberDataCollectPlugin(
                    properties.getProperty(JOB_NUMBER),
                    properties.getProperty(JOB_NAME),
                    properties.getProperty(COLLECT_URL),
                    String.join(":", runtimeOptions.getGlue())
            );
            runtimeOptions.addPlugin(formatter);
        }

        return super.createRuntime(resourceLoader, classLoader, runtimeOptions);
    }
}
