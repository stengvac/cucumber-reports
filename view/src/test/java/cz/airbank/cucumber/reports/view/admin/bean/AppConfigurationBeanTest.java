package cz.airbank.cucumber.reports.view.admin.bean;

import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cz.airbank.cucumber.reports.view.admin.model.AppConfig;
import cz.airbank.cucumber.reports.view.admin.service.ConfigService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Tests for {@link AppConfigurationBean}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class AppConfigurationBeanTest {

    @Mock
    private ConfigService configService;

    @Mock
    private JSFUtilsService jsfUtilsService;

    @InjectMocks
    private AppConfigurationBean bean;

    /**
     * Deep copy is created when bean is initialized.
     */
    @Test
    public void testInit_deepCopy() throws Exception {
        AppConfig cachedConfig = new AppConfig();

        when(configService.retrieveApplicationConfig()).thenReturn(cachedConfig);

        bean.init();
        //bean init retrieve cached config and create deep copy co unsaved changes are not propagated
        assertNotSame(cachedConfig, bean.getAppConfig());
    }

    /**
     * Data propagated to service.
     */
    @Test
    public void testSubmit_serviceCalled() {
        AppConfig appConfig = new AppConfig();

        ReflectionTestUtils.setField(bean, "appConfig", appConfig);

        bean.submitChanges();

        verify(jsfUtilsService).postInfoMessage("Saved.");
        verify(configService).storeApplicationConfig(appConfig);
    }

}