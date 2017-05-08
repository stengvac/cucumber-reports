package cz.airbank.cucumber.reports.dao.mongo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import cz.airbank.cucumber.reports.dao.AppConfigDao;
import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Tests for {@link AppConfigDao} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class AppConfigDaoMongoImplTest {

    @Mock
    private MongoTemplate template;

    @Spy
    @InjectMocks
    private AppConfigDaoMongoImpl dao;

    /**
     * For multiple config objects in DB (some mistake) return only first one.
     */
    @Test
    public void testRetrieveApplicationConfig_retrieveOnlyFirst() {
        ApplicationConfig firstConfig = new ApplicationConfig();
        ApplicationConfig secondConfig = new ApplicationConfig();

        when(template.findAll(ApplicationConfig.class)).thenReturn(Arrays.asList(firstConfig, secondConfig));

        assertSame(firstConfig, dao.retrieveApplicationConfig());
    }

    /**
     * If config is not in DB return null.
     */
    @Test
    public void testRetrieveApplicationConfig_noResultsReturnNull() {
        when(template.findAll(ApplicationConfig.class)).thenReturn(null);

        assertNull(dao.retrieveApplicationConfig());
    }

    /**
     * From config in DB obtain Id and update object.
     */
    @Test
    public void testStoreApplicationConfig() {
        ApplicationConfig foundConfig = new ApplicationConfig();
        foundConfig.setId(SampleData.ID_KEY);
        ApplicationConfig modifiedConfig = new ApplicationConfig();

        doReturn(foundConfig).when(dao).retrieveApplicationConfig();

        dao.storeApplicationConfig(modifiedConfig);

        assertEquals(SampleData.ID_KEY, modifiedConfig.getId());

        verify(template).insert(modifiedConfig);
    }
}