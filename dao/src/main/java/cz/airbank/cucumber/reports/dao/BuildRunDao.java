package cz.airbank.cucumber.reports.dao;

import java.util.List;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;

/**
 * DAO layer for builds.
 *
 * @author David Passler
 */
public interface BuildRunDao {

    /**
     * @return full {@link BuildRunWithTestSuitesTo} entities which provide necessary data
     * to provide detailed insight of build run.
     */
    List<BuildRunWithTestSuitesTo> getLatestBuildsPerProject();

    /**
     * @return list of {@link BuildRun} which are associated to module's last build.
     */
    List<BuildRun> getAllModulesWithLatestBuild();

    /**
     * @param buildName the name of the build
     *
     * @return the latest build specified by it's name
     */
    BuildRun findLatestBuildByName(String buildName);

    /**
     * Try to find build run for given build name and build number.
     *
     * @param buildName the name of the build
     * @param sequentialNumber the number of the build
     */
    BuildRun findByBuildNameAndSequentialNumber(String buildName, long sequentialNumber);

    /**
     * Try to find build run by id.
     *
     * @param id to search by
     *
     * @return found id or null.
     */
    BuildRun findById(String id);

    /**
     * For given id try to find build run metadata.
     *
     * @param id to search by
     *
     * @return metadata or
     *         {@literal null} when
     */
    BuildRunMetadataWithIdTo findBuildRunMetadataWithIdById(String id);

    /**
     * For all builds in DB return given number of buildRunsPerProject runs - the newest ones.
     *
     * @param buildRunsPerProject number of build runs to include per one build
     *
     * @return map with keys as {@link DaoBuildRunMetadata#getProjectName()} and as values list of latest build runs with {@link DaoBuildRunMetadata#getProjectName()}
     */
    List<ProjectWithRunsTo> getLatestBuildsPerProject(int buildRunsPerProject);

    /**
     * Find build run by given args, but include only necessary fields {@link BuildRun#id} and
     * {@link BuildRun#metadata}.
     *
     * @param buildName to search by
     * @param sequentialNumber to search by
     * @return found build run or {@code null} when not found
     */
    BuildRun findBuildPresentationByBuildNameAndSequentialNumber(String buildName, long sequentialNumber);

    /**
     * Find build runs only ({@link BuildRun#id} and {@link BuildRun#metadata}) with build name eq
     * to argument.
     *
     * @param buildName to search by
     * @param maxRecords to retrieve
     * @return list of found results or empty list if there are no results
     */
    List<BuildRun> findBuildPresentationByBuildName(String buildName, int maxRecords);

    /**
     * For given build run try to find his predecessor of this build.
     *
     * @param projectName to search
     * @param sequentialNumber of this build run
     *
     * @return first record with lower sequential number as predecessor of build run spec by params.
     */
    BuildRunMetadataWithIdTo findPreviousBuildId(String projectName, long sequentialNumber);

    /**
     * For given build run try to find id of successor.
     *
     * @param projectName to search
     * @param sequentialNumber of this build run
     *
     * @return first record with greater sequential number as successor of build run spec by params.
     */
    BuildRunMetadataWithIdTo findNextBuildId(String projectName, long sequentialNumber);

    /**
     * Find build run presentation for given id.
     *
     * @param id to search by
     *
     * @return {@code null} if not found
     */
    BuildRunWithTestSuitesTo findBuildRunReport(String id);
}
