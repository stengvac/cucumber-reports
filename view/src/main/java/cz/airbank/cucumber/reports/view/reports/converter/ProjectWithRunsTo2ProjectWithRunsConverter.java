package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;

/**
 * Converter between DAO TO and view object representing build runs grouped by project name.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    BuildRunWithTestSuitesTo2BuildRunWithCountsMapper.class
})
public interface ProjectWithRunsTo2ProjectWithRunsConverter extends Converter<ProjectWithRunsTo, ProjectWithRuns> {

    @Mappings({
        @Mapping(source = "buildRunWithTestSuitesTos", target = "buildRunWithCountsList")
    })
    @Override
    ProjectWithRuns convert(ProjectWithRunsTo source);
}
