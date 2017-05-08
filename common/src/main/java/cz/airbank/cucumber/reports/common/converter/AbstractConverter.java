package cz.airbank.cucumber.reports.common.converter;

/**
 * Abstract converter.
 *
 * @author Vaclav Stengl
 */
public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    @Override
    public T convert(S s) {
        return s == null ? null : convertIntern(s);
    }


    /**
     * Perform conversion between {@link S} and {@link T}
     *
     * @param s to convert
     * @return converted {@link T} or null when arg was null
     */
    protected abstract T convertIntern(S s);
}
