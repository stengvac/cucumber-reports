package cz.airbank.cucumber.reports.dao.mongo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.FeatureRunDao;
import cz.airbank.cucumber.reports.dao.converter.FeatureReportToConverter;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;

/**
 * Implementation of {@link FeatureRunDao}.
 *
 * @author Vaclav Stengl
 */
@Component
public class FeatureRunDaoImpl implements FeatureRunDao {

    private final MongoTemplate repository;
    private final BuildRunDao buildRunDao;
    private final FeatureReportToConverter featureReportToConverter;

    @Autowired
    public FeatureRunDaoImpl(MongoTemplate repository, BuildRunDao buildRunDao,
                             FeatureReportToConverter featureReportToConverter) {
        this.repository = repository;
        this.buildRunDao = buildRunDao;
        this.featureReportToConverter = featureReportToConverter;
    }

    @Override
    public FeatureReportTo findFeatureReportByIds(String buildId, String testSuiteId, int featureIndex) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(testSuiteId));
        query.addCriteria(Criteria.where("featureRuns.id").is(featureIndex));

        TestSuiteExecution execution = repository.findOne(query, TestSuiteExecution.class);
        BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = buildRunDao.findBuildRunMetadataWithIdById(buildId);

        if (execution != null && execution.getFeatureRuns().size() > 0) {
            boolean found = false;
            for (FeatureRun featureRun : execution.getFeatureRuns()) {
                if (featureIndex == featureRun.getId()) {
                    execution.setFeatureRuns(Collections.singletonList(featureRun));
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException("Non existent feature id " + String.valueOf(featureIndex));
            }
        }

        //TODO ask passler
//        List<AggregationOperation> list = new ArrayList<>();
//        list.add(Aggregation.match(Criteria.where("_id").is(testSuiteId)));
//        list.add(Aggregation.project("featureRuns", "metadata", "id"));
//        list.add(Aggregation.unwind("featureRuns"));
//        list.add(Aggregation.match(Criteria.where("featureRuns._id").is(featureIndex)));
//        list.add(Aggregation.group("featureRuns.id", "featureRuns.background", "featureRuns.scenarioDefinitions", "featureRuns.metadata").push("featureRuns").as("featureRuns"));
//        list.add(Aggregation.project("featureRuns", "metadata", "id"));
//        TypedAggregation<TestSuiteExecution> agg = Aggregation.newAggregation(TestSuiteExecution.class, list);
//        List<TestSuiteExecution> mappedResults = repository.aggregate(agg, TestSuiteExecution.class, TestSuiteExecution.class).getMappedResults();


        return featureReportToConverter.convert(execution, buildRunMetadataWithIdTo);
    }
}
