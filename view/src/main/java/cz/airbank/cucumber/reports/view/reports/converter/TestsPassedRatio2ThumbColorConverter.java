package cz.airbank.cucumber.reports.view.reports.converter;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cz.airbank.cucumber.reports.common.converter.Converter;

/**
 * Supportive converter between passed ratio of tests/builds/test suites/features and color used to represent result.
 *
 * @author Vaclav Stengl
 */
@Named
@Scope
public class TestsPassedRatio2ThumbColorConverter implements Converter<Double, String> {

    private static final Logger sLog = LoggerFactory.getLogger(TestsPassedRatio2ThumbColorConverter.class);

    /**
     * Color used for builds with pass ratio => 0.75. It seams build is nearly ok.
     */
    public static final String THUMB_COLOR_OK = "success";

    /**
     * There are several more failed tests. Passed ratio (0.75, 0.25)
     */
    public static final String THUMB_COLOR_SOME_FAILED = "warning";

    /**
     * Nearly all (or all) tests failed. For passed ratio < 0.25
     */
     public static final String THUMB_COLOR_EPIC_FAIL = "danger";

    /**
     * Compute thumb color according to passed scenarios ratio.
     * See constants for more info about results.
     *
     * @param passedRatio to process
     * @return one of three possible color for thumb up/down
     */
    @Override
    public String convert(Double passedRatio) {
        if (passedRatio == null) {
            sLog.debug("Passed ratio was null - the hell happened?");
            return null;
        } else if(passedRatio > 1 || passedRatio < 0) {
            sLog.debug("Passed ratio out of bounds.. with value {}", passedRatio);
        }

        if (passedRatio >= 0.75) {
            return THUMB_COLOR_OK;
        } else if (passedRatio >= 0.25) {
            return THUMB_COLOR_SOME_FAILED;
        }

        return THUMB_COLOR_EPIC_FAIL;
    }
}
