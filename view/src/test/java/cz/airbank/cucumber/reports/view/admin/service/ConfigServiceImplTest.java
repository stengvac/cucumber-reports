package cz.airbank.cucumber.reports.view.admin.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cz.airbank.cucumber.reports.dao.AppConfigDao;
import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;
import cz.airbank.cucumber.reports.view.admin.converter.ApplicationConfig2AppConfigConverter;
import cz.airbank.cucumber.reports.view.admin.model.AppConfig;

/**
 * Tests for {@link ConfigService} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceImplTest {

    @Mock
    private AppConfigDao appConfigDao;

    @Mock
    private ApplicationConfig2AppConfigConverter configConverter;

    @InjectMocks
    private ConfigServiceImpl configService;

    /**
     * After first call config is stored in memory.
     */
    @Test
    public void testRetrieveApplicationConfig_cacheConfig() {
        //cache is empty for now
        assertNull(ReflectionTestUtils.getField(configService, "cachedConfig"));
        ApplicationConfig daoRetrievedConfig = new ApplicationConfig();
        AppConfig configToCache = new AppConfig();

        when(appConfigDao.retrieveApplicationConfig()).thenReturn(daoRetrievedConfig);
        when(configConverter.convert(daoRetrievedConfig)).thenReturn(configToCache);

        AppConfig retrievedConfig = configService.retrieveApplicationConfig();

        assertSame(configToCache, retrievedConfig);

        retrievedConfig = configService.retrieveApplicationConfig();

        assertSame(configToCache, retrievedConfig);

        //called only 1x
        verify(appConfigDao).retrieveApplicationConfig();
    }

    /**
     * Config in DB and in cache updated.
     */
    @Test
    public void testStoreApplicationConfig_cacheUpdated() {
        AppConfig modifiedConfig = new AppConfig();
        ApplicationConfig convertedModifiedConfig = new ApplicationConfig();
        ApplicationConfig modifiedConfigFromDB = new ApplicationConfig();
        AppConfig newlyRetrievedConfig = new AppConfig();

        when(configConverter.convertBackward(modifiedConfig)).thenReturn(convertedModifiedConfig);
        when(appConfigDao.retrieveApplicationConfig()).thenReturn(modifiedConfigFromDB);
        when(configConverter.convert(modifiedConfigFromDB)).thenReturn(newlyRetrievedConfig);

        configService.storeApplicationConfig(modifiedConfig);

        assertSame(newlyRetrievedConfig, ReflectionTestUtils.getField(configService, "cachedConfig"));

        verify(configConverter).convertBackward(modifiedConfig);
        verify(appConfigDao).storeApplicationConfig(convertedModifiedConfig);
    }
}