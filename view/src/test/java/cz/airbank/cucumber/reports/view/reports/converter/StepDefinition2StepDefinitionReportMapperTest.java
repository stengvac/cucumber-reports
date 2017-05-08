package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.Argument;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataTable;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Tests for {@link StepDefinition2StepDefinitionReportMapper}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class StepDefinition2StepDefinitionReportMapperTest {

    @Mock
    private RowTableDefinition2DataTableConverter dataTableConverter;

    @Mock
    private Argument2ArgumentConverter argumentConverter;

    @InjectMocks
    private StepDefinition2StepDefinitionReportMapper testedConverter
            = new StepDefinition2StepDefinitionReportMapperImpl();

    /**
     * Test conversion for not null input.
     */
    @Test
    public void testConversion_notNullInput() {
        StepDefinition stepDefinition = SampleData.createStepDefinition();
        Argument argument = stepDefinition.getArguments().get(0);
        cz.airbank.cucumber.reports.view.reports.model.feature.Argument expectedArgument
                = new cz.airbank.cucumber.reports.view.reports.model.feature.Argument();
        DataTable expectedDataTable = new DataTable();

        when(argumentConverter.convert(argument)).thenReturn(expectedArgument);
        when(dataTableConverter.convert(stepDefinition.getStepDataTable())).thenReturn(expectedDataTable);

        StepDefinitionReport converted = testedConverter.convert(stepDefinition);

        assertEquals(1, converted.getArguments().size());
        assertSame(expectedArgument, converted.getArguments().get(0));
        assertSame(expectedDataTable, converted.getStepDataTable());
        assertEquals(SampleData.STEP_KEYWORD, converted.getKeyword());
        assertEquals(SampleData.STEP_NAME, converted.getStepDefinition());
    }
}