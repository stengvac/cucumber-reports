package cz.airbank.cucumber.reports.dao.mongo;

import com.mongodb.MongoException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;

import cz.airbank.cucumber.reports.dao.DaoException;

/**
 * Intercept mongo exceptions and transform them into dao exceptions.
 * 
 * @author Vaclav Stengl
 */
@Aspect
public class MongoExceptionAspect {

    /**
     * Intercept every method inside package specified by annotation.
     */
    @Pointcut("within(cz.airbank.cucumber.reports.dao.mongo.*)")
    public void anyMethodInsideMongoPackage() {
        //nothing to do here
    }

    /**
     * From all {@link MongoException} and {@link DataAccessException} creates {@link DaoException}.
     * 
     * @param pjp to process
     * @throws Throwable
     */
    @Around("anyMethodInsideMongoPackage()")
    public Object transformMongoExceptions(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (MongoException | DataAccessException e) {
            throw new DaoException(e);
        }
    }
}
