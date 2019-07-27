package kz.kakimzhanova.delivery.exception;

import org.apache.logging.log4j.Level;

public class ServiceException extends Exception {
    public ServiceException(Level warn, String s) {
    }

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
