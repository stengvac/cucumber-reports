package cz.airbank.cucumber.reports.common.converter;

/**
 * Interface, which declare generic convert method.
 * Also works as default config for converters created via mapstruct framework.
 *
 * @author Vaclav Stengl
 */
public interface Converter<S, T> {

    /**
     * Convert {@link S} to {@link T}.
     *
     * @param source the source to be converted
     * @return the converted entity or null for null argument
     */
    T convert(S source);
}
