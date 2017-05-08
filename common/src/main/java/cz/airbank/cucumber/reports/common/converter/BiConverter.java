package cz.airbank.cucumber.reports.common.converter;

/**
 * Interface, which declare generic convert method.
 * Also works as default config for converters created via mapstruct framework.
 *
 * @author Vaclav Stengl
 */
public interface BiConverter<S, S2, T> {

    /**
     * Convert {@link S} and {@link S2} to {@link T}.
     *
     * @param source the source to be converted
     * @return the converted entity or null for null argument
     */
    T convert(S source, S2 source2);
}