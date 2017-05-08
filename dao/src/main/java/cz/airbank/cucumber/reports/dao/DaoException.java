package cz.airbank.cucumber.reports.dao;

/**
 * Transformed exceptions from Datasource.
 * 
 * @author Vaclav Stengl
 */
public class DaoException extends RuntimeException {
    
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
