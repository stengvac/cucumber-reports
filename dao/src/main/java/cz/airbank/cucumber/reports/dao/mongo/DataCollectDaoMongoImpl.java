package cz.airbank.cucumber.reports.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.DataCollectDao;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;

/**
 * Data collect dao impl.
 *
 * @author Vaclav Stengl
 */
@Component
public class DataCollectDaoMongoImpl implements DataCollectDao {

    private final MongoTemplate template;

    @Autowired
    public DataCollectDaoMongoImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public void persist(BuildRun buildRun) {
        template.save(buildRun);
    }

    @Override
    public void persist(TestSuiteExecution execution) {
        template.save(execution);
    }
}
