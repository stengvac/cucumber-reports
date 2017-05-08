package cz.airbank.cucumber.reports.dao.mongo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.dao.TestSuiteExecutionDao;
import cz.airbank.cucumber.reports.dao.converter.TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverter;
import cz.airbank.cucumber.reports.dao.converter.TestSuiteExecution2TestSuiteMetadataWithIdToConverter;
import cz.airbank.cucumber.reports.dao.converter.TestSuiteReportToConverter;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;

/**
 * Mongo implementation of {@link TestSuiteExecutionDao}.
 *
 * @author Vaclav Stengl
 */
@Component
public class TestSuiteExecutionDaoImpl implements TestSuiteExecutionDao {

    private final MongoTemplate template;

    private final TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverter testSuiteMetadataWithIdToConverter;
    private final TestSuiteReportToConverter testSuiteReportToConverter;
    private final TestSuiteExecution2TestSuiteMetadataWithIdToConverter testSuitePresentationTOConverter;

    @Autowired
    public TestSuiteExecutionDaoImpl(MongoTemplate template,
                                     TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverter testSuiteMetadataWithIdToConverter,
                                     TestSuiteReportToConverter testSuiteReportToConverter,
                                     TestSuiteExecution2TestSuiteMetadataWithIdToConverter testSuitePresentationTOConverter) {
        this.template = template;
        this.testSuiteMetadataWithIdToConverter = testSuiteMetadataWithIdToConverter;
        this.testSuiteReportToConverter = testSuiteReportToConverter;
        this.testSuitePresentationTOConverter = testSuitePresentationTOConverter;
    }

    @Override
    public List<TestSuiteMetadataWithIdTo> findTestSuiteMetadataWithId(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(ids));
        query.fields().include("_id");
        query.fields().include("metadata");

        List<TestSuiteExecution> executions = template.find(query, TestSuiteExecution.class);

        return executions.stream().map(
            testSuitePresentationTOConverter::convert
        ).collect(Collectors.toList());
    }

    @Override
    public TestSuiteReportTo findTestSuiteReport(String testSuiteId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(testSuiteId));
        query.fields().include("metadata");
        query.fields().include("featureRuns._id");
        query.fields().include("featureRuns.metadata");

        TestSuiteExecution testSuiteExecution = template.findOne(query, TestSuiteExecution.class);

        return testSuiteReportToConverter.convert(testSuiteExecution);
    }

    @Override
    public List<TestSuiteWithFeaturesMetadataTo> findTestSuitesMetadataWithIds(List<String> ids) {
        Assert.notNull(ids);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(ids));
        query.fields()
            .include("metadata")
            .include("id")
            .include("featureRuns.metadata");

        List<TestSuiteExecution> executions = template.find(query, TestSuiteExecution.class);

        return executions.stream().map(
            testSuiteMetadataWithIdToConverter::convert
        ).collect(Collectors.toList());
    }
}
