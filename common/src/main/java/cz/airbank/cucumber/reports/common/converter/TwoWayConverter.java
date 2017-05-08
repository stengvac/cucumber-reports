package cz.airbank.cucumber.reports.common.converter;

/**
 * Two way null save converter generic interface.
 *
 * @author Vaclav Stengl
 */
public interface TwoWayConverter<S, T> extends Converter<S, T> {

    /**
     * Perform conversion backwards - using inverse configuration specified on {@link Converter#convert(Object)}.
     *
     * @param t to convert
     * @return {@code null} for {@code null} input.
     */
    S convertBackward(T t);
}
