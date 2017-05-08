package cz.airbank.cucumber.reports.dao.mongo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.TestSuiteExecutionDao;
import cz.airbank.cucumber.reports.dao.converter.BuildRun2BuildRunMetadataWithIdToConverter;
import cz.airbank.cucumber.reports.dao.converter.BuildRun2BuildRunWithTestSuitesToMapper;
import cz.airbank.cucumber.reports.dao.converter.ProjectWithRunsToMapper;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;

/**
 * Implementation for {@link BuildRunDao} which is for MongoDB.
 *
 * @author David Passler
 */
@Component
public class BuildRunDaoMongoDBImpl implements BuildRunDao {

    private static final String METADATA_FIELD = "metadata";
    private static final String FEATURE_RUNS_FIELD = "featureRuns";
    private static final String PROJECT_NAME_FIELD = METADATA_FIELD + ".projectName";
    private static final String ID_FIELD = "id";

    private final MongoTemplate mongoTemplate;
    private final TestSuiteExecutionDao testSuiteExecutionDao;

    private final BuildRun2BuildRunMetadataWithIdToConverter buildRunMetadataWithIdToConverter;
    private final ProjectWithRunsToMapper projectWithRunsToConverter;
    private final BuildRun2BuildRunWithTestSuitesToMapper buildRunWithTestSuitesToMapper;

    @Autowired
    public BuildRunDaoMongoDBImpl(MongoTemplate mongoTemplate,
                                  TestSuiteExecutionDao testSuiteExecutionDao,
                                  BuildRun2BuildRunMetadataWithIdToConverter buildRunMetadataWithIdToConverter,
                                  ProjectWithRunsToMapper projectWithRunsToConverter,
                                  BuildRun2BuildRunWithTestSuitesToMapper buildRunWithTestSuitesToMapper) {
        this.mongoTemplate = mongoTemplate;
        this.testSuiteExecutionDao = testSuiteExecutionDao;
        this.buildRunMetadataWithIdToConverter = buildRunMetadataWithIdToConverter;
        this.projectWithRunsToConverter = projectWithRunsToConverter;
        this.buildRunWithTestSuitesToMapper = buildRunWithTestSuitesToMapper;
    }

    @Override
    public List<BuildRunWithTestSuitesTo> getLatestBuildsPerProject() {
        return getLatestBuildsPerProject(1).stream().map(
                projectWithRunsTo -> projectWithRunsTo.getBuildRunWithTestSuitesTos().get(0)
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectWithRunsTo> getLatestBuildsPerProject(int buildRunsPerProject) {
        Query query = new Query();
        query.fields()
                .include(METADATA_FIELD)
                .include("testSuites.id");

        List<BuildRun> sortedBuildRuns = mongoTemplate.find(query, BuildRun.class);
        sortedBuildRuns.sort(compareByProjectNameAndSeqNumber);

        Map<String, List<BuildRun>> runsGroupedByProjectName = new HashMap<>();
        List<String> testSuiteIds = new ArrayList<>();

        for (BuildRun run : sortedBuildRuns) {
            String projectName = run.getMetadata().getProjectName();

            if (!runsGroupedByProjectName.containsKey(projectName)) {
                runsGroupedByProjectName.put(projectName, new ArrayList<>());
            }

            List<BuildRun> runs = runsGroupedByProjectName.get(projectName);

            if (runs.size() < buildRunsPerProject) {
                runs.add(run);
                run.getTestSuites().forEach(execution ->
                        //test suites, which are needed to be queried
                        testSuiteIds.add(execution.getId())
                );
            }
        }

        List<TestSuiteWithFeaturesMetadataTo> testSuitesMetadataWithIds =
                testSuiteExecutionDao.findTestSuitesMetadataWithIds(testSuiteIds);
        return projectWithRunsToConverter.convert(mapTestSuites(testSuitesMetadataWithIds), runsGroupedByProjectName);
    }

    /**
     * Compare function, which sort by {@link DaoBuildRunMetadata#getProjectName()}
     * and then by {@link DaoBuildRunMetadata#getSequentialNumber()}.
     */
    private Comparator<BuildRun> compareByProjectNameAndSeqNumber = (BuildRun o1, BuildRun o2) -> {
        DaoBuildRunMetadata firstMetadata = o1.getMetadata();
        DaoBuildRunMetadata secondMetadata = o2.getMetadata();

        int projectNameCompare = ObjectUtils.compare(firstMetadata.getProjectName(), secondMetadata.getProjectName());

        if (projectNameCompare == 0) {
            //latest first
            return ObjectUtils.compare(secondMetadata.getSequentialNumber(), firstMetadata.getSequentialNumber());
        }

        return projectNameCompare;
    };


    @Override
    public List<BuildRun> getAllModulesWithLatestBuild() {
        final Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.Direction.DESC, "metadata.buildAt"),
                Aggregation.unwind("testSuites.featureRuns"),
                Aggregation.group(Fields.from(Fields.field("module", "featureRuns.featureMetadata.module"),
                        Fields.field("featureFileName", "featureRuns.featureMetadata.filename")))
                        .first(METADATA_FIELD).as(METADATA_FIELD)
                        .first(FEATURE_RUNS_FIELD).as(FEATURE_RUNS_FIELD),
                Aggregation.group(ID_FIELD)
                        .first(METADATA_FIELD).as(METADATA_FIELD)
                        .push(FEATURE_RUNS_FIELD).as(FEATURE_RUNS_FIELD)
        );

        return mongoTemplate.aggregate(aggregation, BuildRun.class, BuildRun.class).getMappedResults();
    }

    @Override
    public BuildRun findLatestBuildByName(String buildName) {
        Query query = new Query();
        query.addCriteria(Criteria.where(PROJECT_NAME_FIELD).is(buildName));
        query.with(new Sort(Sort.Direction.DESC, "metadata.buildAt"));
        query.limit(1);

        return mongoTemplate.findOne(query, BuildRun.class);
    }

    @Override
    public BuildRun findByBuildNameAndSequentialNumber(String buildName, long sequentialNumber) {
        return mongoTemplate.findOne(createFindByNameAndSeqNumberQuery(buildName, sequentialNumber), BuildRun.class);
    }

    @Override
    public BuildRun findById(String id) {
        return mongoTemplate.findById(id, BuildRun.class);
    }

    @Override
    public BuildRunMetadataWithIdTo findBuildRunMetadataWithIdById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID_FIELD).is(id));
        query.fields().include(METADATA_FIELD);

        BuildRun run = mongoTemplate.findOne(query, BuildRun.class);
        return buildRunMetadataWithIdToConverter.convert(run);
    }

    @Override
    public BuildRun findBuildPresentationByBuildNameAndSequentialNumber(String buildName, long sequentialNumber) {
        Query query = createFindByNameAndSeqNumberQuery(buildName, sequentialNumber);
        query.fields().include(METADATA_FIELD);
        query.fields().include(ID_FIELD);

        return mongoTemplate.findOne(query, BuildRun.class);
    }

    @Override
    public List<BuildRun> findBuildPresentationByBuildName(String buildName, int maxRecords) {
        Query query = new Query(Criteria.where(PROJECT_NAME_FIELD).is(buildName));
        query.limit(maxRecords);
        query.fields().include(METADATA_FIELD);
        query.fields().include(ID_FIELD);

        return mongoTemplate.find(query, BuildRun.class);
    }

    /**
     * Create query which will find build run by args.
     *
     * @param buildName        to search by
     * @param sequentialNumber to search by
     * @return prepared query
     */
    private Query createFindByNameAndSeqNumberQuery(String buildName, long sequentialNumber) {
        return new Query()
                .addCriteria(Criteria.where(PROJECT_NAME_FIELD).is(buildName))
                .addCriteria(Criteria.where("metadata.sequentialNumber").is(sequentialNumber));
    }

    @Override
    public BuildRunMetadataWithIdTo findPreviousBuildId(String projectName, long sequentialNumber) {
        return findPrevOrNextBuildId(projectName, Criteria.where("metadata.sequentialNumber").lt(sequentialNumber));
    }

    @Override
    public BuildRunMetadataWithIdTo findNextBuildId(String projectName, long sequentialNumber) {
        return findPrevOrNextBuildId(projectName, Criteria.where("metadata.sequentialNumber").gt(sequentialNumber));
    }

    /**
     * Find id of build run specified by provided criteria parameter.
     *
     * @param projectName        to search build runs by
     * @param prevOrNextCriteria to specify which id find - prev or next
     * @return {@code null} if there is no match (like next build not does not exist)
     */
    private BuildRunMetadataWithIdTo findPrevOrNextBuildId(String projectName, Criteria prevOrNextCriteria) {
        Query query = new Query();
        query.addCriteria(Criteria.where("metadata.projectName").is(projectName));
        query.addCriteria(prevOrNextCriteria);
        query.with(new Sort(Sort.Direction.ASC, "metadata.sequentialNumber"));
        query.fields()
                .include("_id")
                .include(METADATA_FIELD);

        BuildRun build = mongoTemplate.findOne(query, BuildRun.class);
        return buildRunMetadataWithIdToConverter.convert(build);
    }

    @Override
    public BuildRunWithTestSuitesTo findBuildRunReport(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.fields()
                .include(METADATA_FIELD)
                .include("_id")
                .include("testSuites.id");

        BuildRun buildRun = mongoTemplate.findOne(query, BuildRun.class);

        if (buildRun == null) {
            return null;
        }

        List<TestSuiteExecution> testSuiteExecutions = buildRun.getTestSuites();
        List<String> ids = testSuiteExecutions.stream().map(
                TestSuiteExecution::getId
        ).collect(Collectors.toList());

        List<TestSuiteWithFeaturesMetadataTo> testSuitesMetadataWithIds =
                testSuiteExecutionDao.findTestSuitesMetadataWithIds(ids);

        return buildRunWithTestSuitesToMapper.convert(buildRun, mapTestSuites(testSuitesMetadataWithIds));
    }

    /**
     * Create map for provided list, where as key is used test suite id and test suite as value.
     *
     * @param testSuiteWithFeatures to map
     * @return mapped test suites
     */
    private Map<String, TestSuiteWithFeaturesMetadataTo> mapTestSuites(List<TestSuiteWithFeaturesMetadataTo> testSuiteWithFeatures) {
        Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteMappedById = new HashMap<>();
        testSuiteWithFeatures.forEach(
                testSuite -> testSuiteMappedById.put(testSuite.getTestSuiteMetadataWithIdTo().getId(), testSuite)
        );

        return testSuiteMappedById;
    }
}
