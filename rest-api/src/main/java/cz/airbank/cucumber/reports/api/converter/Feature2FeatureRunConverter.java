package cz.airbank.cucumber.reports.api.converter;

import java.util.List;
import java.util.function.Consumer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.transport.model.Feature;

/**
 * Perform conversion between Feature transport object and DAO feature run entity.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        ScenarioDefinition2ScenarioDefinitionConverter.class,
        LineStatement2LineStatementConverter.class,
        LineRange2LineRangeConverter.class
})
public abstract class Feature2FeatureRunConverter implements Converter<Feature, FeatureRun> {

    @Mappings({
        @Mapping(ignore = true, target = "id"),
        @Mapping(source = "scenarioDefinitionList", target = "scenarioDefinitions"),
        @Mapping(source = "featureMetadata", target = "metadata")
    })
    @Override
    public abstract FeatureRun convert(Feature source);

    /**
     * Perform conversion between TO and DAO entity feature metadata.
     *
     * @param metadata to convert
     */
    protected DaoFeatureMetadata convertFeatureMetadata(cz.airbank.cucumber.reports.transport.model.FeatureMetadata metadata) {
        Assert.notNull(metadata);

        DaoFeatureMetadata daoMetadata = new DaoFeatureMetadata();

        daoMetadata.setFilename(metadata.getFilename());
        daoMetadata.setGlue(metadata.getGlue());
        daoMetadata.setModule(metadata.getModule());

        return daoMetadata;
    }

    /**
     * Fill dao metadata with nearly all information needed for statistics purposes.
     *
     * @param featureRun the data source
     */
    //TODO test needed
    @AfterMapping
    protected void fillMetadata(@MappingTarget FeatureRun featureRun) {
        DaoFeatureMetadata metadata = featureRun.getMetadata();

        if (featureRun.getBackground() != null) {
            metadata.setBackgroundStepDefinitionsTotalCount(featureRun.getBackground().getStepDefinitions().size());
        }

        for (cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition scenarioDefinition : featureRun.getScenarioDefinitions()) {
            metadata.incrementScenarioDefinitions();
            metadata.addScenarioStepDefinitions(scenarioDefinition.getStepDefinitions().size());
            metadata.addScenarioExecutionsTotal(scenarioDefinition.getScenarioExecutions().size());

            for (ScenarioExecution execution : scenarioDefinition.getScenarioExecutions()) {
                if (processStepRuns(execution, metadata)) {
                    metadata.incrementScenarioExecutionsPassed();
                }

                metadata.addScenarioStepExecutionsTotal(execution.getScenarioStepRuns().size());
                metadata.addBackgroundStepExecutionsTotal(execution.getBackgroundStepRuns().size());
            }
        }
    }

    /**
     * Process step runs. Fill metadata with step results.
     *
     * @param execution to process
     * @param metadata to fill with data
     *
     * @return {@code true} when status of all step runs is {@link StepStatus#PASSED}
     */
    private boolean processStepRuns(ScenarioExecution execution, DaoFeatureMetadata metadata) {
        //hooks are not counted as steps, only increase duration
        boolean beforeHooksPassed = processResults(execution.getBeforeHookRuns(), metadata, (dummy) -> {});
        boolean afterHooksPassed = processResults(execution.getAfterHookRuns(), metadata, (dummy) -> {});

        boolean backgroundStepsPassed = processResults(execution.getBackgroundStepRuns(), metadata,
                DaoFeatureMetadata::incrementBackgroundStepExecutionPassed);
        boolean scenarioStepsPassed = processResults(execution.getScenarioStepRuns(), metadata,
                DaoFeatureMetadata::incrementScenarioStepExecutionsPassed);

        //all results must pass so execution can be successful
        return beforeHooksPassed && backgroundStepsPassed && scenarioStepsPassed && afterHooksPassed;
    }

    /**
     * Process results of executed steps and hooks in scenario execution context.
     *
     * @param stepRuns to process
     * @param metadata to store obtained data in
     * @param increaseResultsPassed the function used to increase number of passed results if needed
     * @return {@code true} when all results eq {@link StepStatus#PASSED}
     */
    private boolean processResults(List<StepRun> stepRuns, DaoFeatureMetadata metadata,
                                   Consumer<DaoFeatureMetadata> increaseResultsPassed) {
        boolean executionPassed = true;

        for (StepRun stepRun : stepRuns) {
            metadata.increaseTotalDuration(stepRun.getDuration());
            if (StepStatus.PASSED.equals(stepRun.getStepStatus())) {
                increaseResultsPassed.accept(metadata);
            } else {
                executionPassed = false;
            }
        }

        return executionPassed;
    }
}
