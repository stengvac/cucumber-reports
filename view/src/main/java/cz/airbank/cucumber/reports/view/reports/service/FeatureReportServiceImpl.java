package cz.airbank.cucumber.reports.view.reports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.airbank.cucumber.reports.dao.FeatureRunDao;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.view.reports.converter.ScenarioRun2FeatureReportConverter;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureReport;

/**
 * Service for feature report purposes.
 *
 * @author Vaclav Stengl
 */
@Service
public class FeatureReportServiceImpl implements FeatureReportService {

    private final FeatureRunDao featureRunDao;
    private final ScenarioRun2FeatureReportConverter converter;

    @Autowired
    public FeatureReportServiceImpl(FeatureRunDao featureRunDao,
                                    ScenarioRun2FeatureReportConverter converter) {
        this.converter = converter;
        this.featureRunDao = featureRunDao;
    }

    @Override
    public FeatureReport getFeatureReportData(String buildId, String testSuiteId, int featureRunId) {
        FeatureReportTo featureReportTo = featureRunDao.findFeatureReportByIds(buildId, testSuiteId, featureRunId);

        return converter.convert(featureReportTo);
    }
}
