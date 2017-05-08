package cz.airbank.cucumber.reports.api.converter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.mockito.Mock;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.DescribedStatement;
import cz.airbank.cucumber.reports.dao.entity.LineRange;
import cz.airbank.cucumber.reports.dao.entity.LineStatement;
import cz.airbank.cucumber.reports.dao.entity.Statement;
import cz.airbank.cucumber.reports.dao.entity.TaggedStatement;
import cz.airbank.cucumber.reports.transport.model.TagStatement;

/**
 * Provide common functionality for classes, which derive from
 * {@link cz.airbank.cucumber.reports.transport.model.Statement}
 * {@link cz.airbank.cucumber.reports.transport.model.DescribedStatement}
 * {@link TagStatement}
 *
 * @author Vaclav Stengl
 */
public abstract class CommonAttributesTestHelper {

    @Mock
    protected LineStatement2LineStatementConverter lineStatementConverter;

    @Mock
    protected LineRange2LineRangeConverter lineRangeConverter;

    protected LineStatement expectedComment;
    protected LineRange expectedRange;
    protected LineStatement expectedTag;

    /**
     * 1, Create instances of expected objects to be returned from injected converters
     * 2, for converted object create return rules for mocked converters
     */
    @Before
    public void setUp() {
        expectedComment = new LineStatement();
        expectedRange = new LineRange();
        expectedTag = new LineStatement();
        cz.airbank.cucumber.reports.transport.model.Statement statement = returnStatementToConvert();

        when(lineRangeConverter.convert(statement.getRange())).thenReturn(expectedRange);
        cz.airbank.cucumber.reports.transport.model.LineStatement comment = statement.getCommentList().get(0);
        when(lineStatementConverter.convert(comment)).thenReturn(expectedComment);

        if (statement instanceof TagStatement) {
            TagStatement tagStatement = (TagStatement) statement;
            cz.airbank.cucumber.reports.transport.model.LineStatement tag = tagStatement.getTagList().get(0);
            when(lineStatementConverter.convert(tag)).thenReturn(expectedTag);
        }
    }

    /**
     * Assert attributes present in statement are set correctly.
     *
     * @param statement to assert
     */
    protected void assertStatementAttributes(Statement statement) {
        assertNotNull(statement);
        //comments
        assertEquals(1, statement.getCommentList().size());
        assertSame(expectedComment, statement.getCommentList().get(0));
        //name
        assertEquals(SampleData.STATEMENT_NAME, statement.getName());
        //range
        assertSame(expectedRange, statement.getRange());
    }

    /**
     * Assert attributes present in described statement are set correctly.
     *
     * @param describedStatement to assert
     */
    protected void assertDescribedStatement(DescribedStatement describedStatement) {
        assertStatementAttributes(describedStatement);
        assertEquals(SampleData.STATEMENT_DESCRIPTION, describedStatement.getDescription());
    }

    /**
     * Assert attributes present in tagged statement are set correctly.
     *
     * @param taggedStatement to assert
     */
    protected void assertTaggedStatement(TaggedStatement taggedStatement) {
        assertDescribedStatement(taggedStatement);

        assertEquals(1, taggedStatement.getTagList().size());
        assertSame(expectedTag, taggedStatement.getTagList().get(0));
    }

    /**
     * Return statement which will be converted so mock converters can be initialized.
     */
    protected abstract cz.airbank.cucumber.reports.transport.model.Statement returnStatementToConvert();
}
