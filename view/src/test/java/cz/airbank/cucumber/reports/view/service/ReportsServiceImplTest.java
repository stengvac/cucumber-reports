package cz.airbank.cucumber.reports.view.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.model.BuildDetailWithCounts;
import cz.airbank.cucumber.reports.view.model.ModuleBuildCounts;
import cz.airbank.cucumber.reports.view.reports.converter.DaoBuildRunMetadata2BuildRunMetadataConverter;
import cz.airbank.cucumber.reports.view.reports.converter.FeatureRun2FeatureMetadataWithIdConverter;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.service.ReportsServiceImpl;

/**
 * Test for {@link ReportsServiceImpl}.
 *
 * @author David Passler
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportsServiceImplTest {

    @Mock
    private DaoBuildRunMetadata2BuildRunMetadataConverter buildRunMetadataConverter;

    @Mock
    private FeatureRun2FeatureMetadataWithIdConverter featureRun2FeatureMetadataWithIdConverter;

    @Mock
    private BuildRunDao buildRunDao;

    @InjectMocks
    private ReportsServiceImpl service;

    /**
     * Test for {@link ReportsServiceImpl#getModuleBuildCounts()} when two modules are returned.
     */
    @Test
    public void testGetModuleBuildCounts_success() {
        final int numberOfBuildRuns = 2;
        final int[] featureCounts = {3, 2};

        List<BuildRun> buildRuns = SampleData.createSampleBuildRuns(numberOfBuildRuns, featureCounts);

        BuildRunMetadata metadata1 = new BuildRunMetadata();
        metadata1.setProjectName("projectName0");
        when(buildRunMetadataConverter.convert(buildRuns.get(0).getMetadata())).thenReturn(metadata1);

        BuildRunMetadata metadata22 = new BuildRunMetadata();
        metadata22.setProjectName("projectName1");
        when(buildRunMetadataConverter.convert(buildRuns.get(1).getMetadata())).thenReturn(metadata22);

        when(buildRunDao.getAllModulesWithLatestBuild()).thenReturn(buildRuns);

        final List<ModuleBuildCounts> moduleBuildCounts = service.getModuleBuildCounts();

        final Map<String, Integer> expectedFeatureCountResults = new HashMap<>();
        expectedFeatureCountResults.put("module0", featureCounts[0]);
        expectedFeatureCountResults.put("module1", featureCounts[1]);

        Assert.assertEquals(numberOfBuildRuns, moduleBuildCounts.size());

        for (int i = 0; i < moduleBuildCounts.size(); i++) {
            final ModuleBuildCounts currentModuleBuildCounts = moduleBuildCounts.get(i);

            Assert.assertEquals("module" + i, currentModuleBuildCounts.getModuleName());
            Assert.assertEquals("projectName" + i, currentModuleBuildCounts.getBuildRunMetadata().getProjectName());

            int currentFeatureCount = expectedFeatureCountResults.get(currentModuleBuildCounts.getModuleName());

            Assert.assertEquals(currentFeatureCount, currentModuleBuildCounts.getFeatureCount());
            Assert.assertEquals(currentFeatureCount * 12, currentModuleBuildCounts.getScenarioCount());
            Assert.assertEquals(currentFeatureCount * 12, currentModuleBuildCounts.getStepCount());
        }
    }

    /**
     * Test for {@link ReportsServiceImpl#getModuleBuildCounts()} when no modules are returned.
     */
    @Test
    public void testGetModuleBuildCounts_empty() {
        when(buildRunDao.getAllModulesWithLatestBuild()).thenReturn(new ArrayList<>());

        final List<ModuleBuildCounts> moduleBuildCounts = service.getModuleBuildCounts();
        Assert.assertEquals(0, moduleBuildCounts.size());
    }

    /**
     * Test for {@link ReportsServiceImpl#getLatestBuildDetail(String)} when build is returned.
     */
    @Test
    public void testGetBuildDetail_success() {
        BuildRunMetadata metadata = new BuildRunMetadata();
        metadata.setProjectName("projectName0");

        final int[] featureCount = {3};
        final List<BuildRun> sampleBuildRuns = SampleData.createSampleBuildRuns(1, featureCount);

        when(buildRunDao.findLatestBuildByName("buildDetail")).thenReturn(sampleBuildRuns.get(0));
        when(buildRunMetadataConverter.convert(sampleBuildRuns.get(0).getMetadata())).thenReturn(metadata);
        final BuildDetailWithCounts moduleBuildCounts = service.getLatestBuildDetail("buildDetail");

        Assert.assertEquals(1, moduleBuildCounts.getCounts().size());

        for (int i = 0; i < moduleBuildCounts.getCounts().size(); i++) {
            final ModuleBuildCounts currentModuleBuildCounts = moduleBuildCounts.getCounts().get(i);

            Assert.assertEquals("module" + i, currentModuleBuildCounts.getModuleName());
            Assert.assertEquals("projectName" + i, currentModuleBuildCounts.getBuildRunMetadata().getProjectName());

            Assert.assertEquals(3, currentModuleBuildCounts.getFeatureCount());
            Assert.assertEquals(3 * 12, currentModuleBuildCounts.getScenarioCount());
            Assert.assertEquals(3 * 12, currentModuleBuildCounts.getStepCount());
        }
    }

    /**
     * Test for {@link ReportsServiceImpl#getLatestBuildDetail(String)} when no module found.
     */
    @Test(expected = IllegalStateException.class)
    public void testGetBuildDetail_empty() {
        when(buildRunDao.findLatestBuildByName("buildDetail")).thenReturn(null);

        service.getLatestBuildDetail("buildDetail");
    }

}