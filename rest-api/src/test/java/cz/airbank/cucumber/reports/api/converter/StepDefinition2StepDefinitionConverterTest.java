package cz.airbank.cucumber.reports.api.converter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.transport.model.Argument;
import cz.airbank.cucumber.reports.transport.model.Statement;

/**
 * Tests for {@link StepDefinition2StepDefinitionConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class StepDefinition2StepDefinitionConverterTest extends CommonAttributesTestHelper {

    @Mock
    private Argument2ArgumentConverter argumentConverter;

    @Mock
    private DataTable2RowTableDefinitionConverter rowTableDefinitionConverter;

    @InjectMocks
    private StepDefinition2StepDefinitionConverter converter = new StepDefinition2StepDefinitionConverterImpl();

    private cz.airbank.cucumber.reports.transport.model.StepDefinition stepDefinition = SampleData.createStepDefinition();

    @Override
    protected Statement returnStatementToConvert() {
        return stepDefinition;
    }

    /**
     * Test whenever conversion between step definitions is ok.
     */
    @Test
    public void testConvert_notNullInput() {
        List<Argument> arguments = stepDefinition.getArguments();
        RowTableDefinition exceptedDataTable = new RowTableDefinition();
        cz.airbank.cucumber.reports.dao.entity.Argument expectedArgument
                = new cz.airbank.cucumber.reports.dao.entity.Argument();

        when(rowTableDefinitionConverter.convert(stepDefinition.getStepDataTable())).thenReturn(exceptedDataTable);
        when(argumentConverter.convert(arguments.get(0))).thenReturn(expectedArgument);

        StepDefinition converted = converter.convert(stepDefinition);

        //name also contains keyword
        assertEquals(SampleData.STEP_KEYWORD + " " + SampleData.STATEMENT_NAME, converted.getName());
        assertEquals(1, converted.getCommentList().size());
        assertSame(expectedComment, converted.getCommentList().get(0));
        //range
        assertSame(expectedRange, converted.getRange());
        assertSame(exceptedDataTable, converted.getStepDataTable());
        assertEquals(1, converted.getArguments().size());
        assertSame(expectedArgument, converted.getArguments().get(0));
    }
}