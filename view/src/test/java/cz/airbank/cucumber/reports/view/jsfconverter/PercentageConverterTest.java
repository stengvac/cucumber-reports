package cz.airbank.cucumber.reports.view.jsfconverter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.service.I18nService;

/**
 * Tests for {@link PercentageConverterTest}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class PercentageConverterTest {

    @Mock
    private I18nService i18nService;

    @InjectMocks
    private PercentageConverter percentageConverter;

    /**
     * This operation is not implemented -> UnsupportedOperationException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getAsObject() throws Exception {
        percentageConverter.getAsObject(null, null, null);
    }

    /**
     * Format percentage for given double value.
     */
    @Test
    public void getAsString() throws Exception {
        when(i18nService.getLocale()).thenReturn(Locale.ENGLISH);

        String formattedPercentage = percentageConverter.getAsString(null, null, 0.5);

        assertEquals("50%", formattedPercentage);
    }
}