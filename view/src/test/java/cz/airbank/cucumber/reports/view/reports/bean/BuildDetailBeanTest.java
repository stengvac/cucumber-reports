package cz.airbank.cucumber.reports.view.reports.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;
import cz.airbank.cucumber.reports.view.service.ReportsServiceMockImpl;

/**
 * Test for {@link BuildDetailBean}.
 *
 * @author David Passler
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildDetailBeanTest {

    @InjectMocks
    private BuildDetailBean bean;

    @Spy
    private ReportsService reportsService = new ReportsServiceMockImpl();

    @Mock
    private JSFUtilsService jsfUtilsService;

    /**
     * Test for {@link BuildDetailBean#getBuildRunReport()} when no build name param is passed.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBuildDetail_noBuildName() {
        Mockito.when(jsfUtilsService.getPageParameter(BuildDetailBean.BUILD_ID_PARAM_NAME)).thenReturn(null);
        bean.getData();
    }

    /**
     * Test for {@link BuildDetailBean#getBuildRunReport()} when build name param is passed but it is empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBuildDetail_emptyBuildName() {
        Mockito.when(jsfUtilsService.getPageParameter(BuildDetailBean.BUILD_ID_PARAM_NAME)).thenReturn("");
        bean.getData();
    }

}