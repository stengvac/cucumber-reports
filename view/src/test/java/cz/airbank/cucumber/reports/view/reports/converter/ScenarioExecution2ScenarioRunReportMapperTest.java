package cz.airbank.cucumber.reports.view.reports.converter;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioRunReport;

/**
 * Tests for {@link ScenarioExecution2ScenarioRunReportMapper}
 *
 * @author Vaclav Stengl
 */
public class ScenarioExecution2ScenarioRunReportMapperTest {

    private ScenarioExecution2ScenarioRunReportMapper mapper
            = new ScenarioExecution2ScenarioRunReportMapperImpl();

    /**
     * Size of converted runs is ok.
     */
    @Test
    public void testConvert_notNullInput() {
        ScenarioRunReport runReport = mapper.convert(SampleData.createScenarioExecution());

        assertEquals(1, runReport.getBackgroundStepResults().size());
        assertEquals(2, runReport.getScenarioStepResults().size());

        assertEquals(1, runReport.getBeforeHookStepResults().size());
        assertEquals(2, runReport.getAfterHookStepResults().size());
    }

    /**
     * Raw result report is converted.
     */
    @Test
    public void testConvertBaseResultReport_notNullInput() {
        StepRun stepRun = SampleData.createStepRun(StepStatus.FAILED);

        ResultReport converted = mapper.convertBaseResultReport(stepRun);

        assertEquals(SampleData.STEP_ERR_MSG, converted.getErrorMessage());
        assertEquals(SampleData.STEP_DURATION, converted.getDuration());
        assertEquals(StepStatus.FAILED, converted.getStatus());
        assertEquals(1, converted.getEmbeddingList().size());
        assertEquals(SampleData.STEP_EMBEDDING_ID, converted.getEmbeddingList().get(0));
    }
}