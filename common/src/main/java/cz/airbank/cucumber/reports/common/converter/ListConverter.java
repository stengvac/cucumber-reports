package cz.airbank.cucumber.reports.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vaclav Stengl
 */
public interface ListConverter<S, T> extends Converter<S, T> {

    /**
     * Global implementation of conversion method for list.
     *
     * @param sourceList to convert
     * @return empty {@link ArrayList} for {@code null} input
     */
    default List<T> convertList(List<S> sourceList) {
        if (sourceList == null) {
            return new ArrayList<>(0);
        }

        return sourceList.stream().map(this::convert).collect(Collectors.toList());
    }
}
