package cz.airbank.cucumber.reports.dao;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;

/**
 * Dao for data collect.
 *
 * @author Vaclav Stengl
 */
public interface DataCollectDao {

    /**
     * Persist build run. When {@link BuildRun#getId()} is not null update method is called and insert otherwise.
     *
     * @param buildRun to insert or update
     */
    void persist(BuildRun buildRun);

    /**
     * Persist build run. When {@link TestSuiteExecution#getId()} is not null update method is called and insert otherwise.
     *
     * @param execution to insert or update
     */
    void persist(TestSuiteExecution execution);
}
