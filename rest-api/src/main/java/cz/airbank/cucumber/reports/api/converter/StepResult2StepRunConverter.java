package cz.airbank.cucumber.reports.api.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.transport.model.Embedding;
import cz.airbank.cucumber.reports.transport.model.StepResult;

/**
 * Converter between transport and dao step result.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = Argument2ArgumentConverter.class)
public interface StepResult2StepRunConverter extends Converter<StepResult, StepRun> {

    @Mappings({
        @Mapping(source = "embeddingList", target = "embeddedIds"),
        @Mapping(source = "status", target = "stepStatus")
    })
    @Override
    StepRun convert(StepResult source);

    /**
     * Extract ids of embeddings.
     *
     * @param embeddingList to extract ids
     * @return list of embedded file ids
     */
    default List<String> convertEmbeddings(List<Embedding> embeddingList) {
        if (embeddingList == null) {
            return new ArrayList<>();
        }

        return embeddingList.stream().map(
            Embedding::getId
        ).collect(Collectors.toList());
    }

    /**
     * Perform conversion between transport and common step status enums.
     *
     * @param stepStatus to convert
     * @return converted value
     * @throws IllegalArgumentException if arg is null
     */
    default StepStatus convertStepStatus(cz.airbank.cucumber.reports.transport.model.StepStatus stepStatus) {
        Assert.notNull(stepStatus, "Step status can't be null.");

        return cz.airbank.cucumber.reports.common.model.StepStatus.valueOf(stepStatus.name());
    }
}
