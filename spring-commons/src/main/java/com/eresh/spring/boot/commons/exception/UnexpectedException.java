package com.eresh.spring.boot.commons.exception;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class UnexpectedException extends RuntimeException {
    public UnexpectedException() {
        super();
    }

    public UnexpectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
