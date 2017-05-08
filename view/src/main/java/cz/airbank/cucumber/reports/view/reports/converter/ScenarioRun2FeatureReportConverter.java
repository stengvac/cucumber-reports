package cz.airbank.cucumber.reports.view.reports.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Perform conversion between dao object and view object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        ScenarioDefinition2ScenarioDefinitionReportConverter.class,
        TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter.class,
        FeatureRun2FeatureMetadataWithIdConverter.class,
        BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter.class,
        LineStatement2LineStatementReportConverter.class
})
public abstract class ScenarioRun2FeatureReportConverter implements Converter<FeatureReportTo, FeatureReport> {

    @Mappings({
            @Mapping(source = "featureRun.name", target = "name"),
            @Mapping(source = "featureRun.description", target = "description"),
            @Mapping(source = "featureRun.background", target = "background"),
            @Mapping(source = "featureRun.tagList", target = "tagList"),
            @Mapping(source = "featureRun.commentList", target = "commentList"),
            @Mapping(source = "featureRun.scenarioDefinitions", target = "scenarioDefinitionList"),
            @Mapping(source = "buildRunMetadataWithIdTo", target = "buildRunMetadataWithId"),
            @Mapping(source = "testSuiteMetadataWithIdTo", target = "testSuiteMetadataWithId"),
            @Mapping(source = "featureRun", target = "featureMetadataWithId")
    })
    @Override
    public abstract FeatureReport convert(FeatureReportTo featureReportTo);

    /**
     * Link step/hook definitions together + add link from scenario run to scenario definition so
     * scenario run can access examples section if needed.
     *
     * @param report the data source
     */
    @AfterMapping
    protected void setAttributes(@MappingTarget FeatureReport report) {
        final List<StepDefinitionReport> backgroundStepDefinitions;
        if (report.getBackground() != null) {
            backgroundStepDefinitions = report.getBackground().getStepDefinitionReports();
        } else {
            backgroundStepDefinitions = Collections.emptyList();
        }

        report.getScenarioDefinitionList().forEach(scenarioDef -> {
            scenarioDef.getScenarioRunReports().forEach(runReport -> {
                runReport.setReport(scenarioDef);
                //hooks
                setDefinitions(runReport.getBeforeHookStepResults(), scenarioDef.getBeforeHookDefinitionReports());
                setDefinitions(runReport.getAfterHookStepResults(), scenarioDef.getAfterHookDefinitionReports());
                //steps
                setDefinitions(runReport.getBackgroundStepResults(), backgroundStepDefinitions);
                setDefinitions(runReport.getScenarioStepResults(), scenarioDef.getStepDefinitionReports());
            });
        });
    }

    /**
     * Set definitions to results so they are together and easier to access.
     *
     * @param resultReports to set definitions in
     * @param definitions   to set
     */
    private <T> void setDefinitions(List<ResultReport<T>> resultReports, List<T> definitions) {
        for (int i = 0; i < resultReports.size(); i++) {
            T definition = definitions.get(i);
            resultReports.get(i).setDefinitionReport(definition);
        }
    }
}
