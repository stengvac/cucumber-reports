package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.primefaces.model.DefaultStreamedContent;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.EmbeddingTo;

/**
 * Perform conversion between embeddingTo and primefaces streamed content which is used to present images.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface EmbeddingTo2DefaultStreamedContentConverter extends Converter<EmbeddingTo, DefaultStreamedContent> {

    @Mappings({
        @Mapping(target = "name", ignore = true),
        @Mapping(target = "contentEncoding", ignore = true)
    })
    @Override
    DefaultStreamedContent convert(EmbeddingTo source);
}
