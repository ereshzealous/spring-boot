package com.eresh.spring.boot.commons.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.rmi.RemoteException;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class ExceptionLogUtil {
    private ExceptionLogUtil() {
    }

    public static void log(Throwable e, Logger logger) {
        log(e, null, logger);
    }

    public static void log(Throwable e, String msg, Logger logger) {
        if (e instanceof RemoteException && e.getCause() != null) {
            log(e.getCause(), msg, logger);
        } else {
            logExceptionWithRootStackTrace(e, msg, logger);
        }
    }

    public static void logDBError(Logger logger, Level level, String msg, Throwable _t) {
        logNetError(logger, level, msg, _t);
    }

    public static void logNetError(Logger logger, Level level, String msg, Throwable _t) {

    }

    /**
     * The important bit of a stack trace is always the root cause, not all the
     * caught and wrapped and etc.  This method makes sure we log that part of
     * the stack trace
     *
     * @param e
     * @param msg
     * @param logger
     */
    private static void logExceptionWithRootStackTrace(Throwable e, String msg, Logger logger) {
        Level priority = Level.ERROR;
        if (e instanceof Error) {
            priority = Level.TRACE;
        }
        System.out.println(priority);
        StringBuilder output = new StringBuilder(2048);

        output.append(getLoggerMessage(msg));
        output.append(StackTraceUtil.getAnalysisStackTrace(e));

        //logger.log(priority, output.toString());
    }

    private static String getLoggerMessage(String message) {
        StringBuilder output = new StringBuilder();
        if (message != null) {
            output.append(message);
        }
        output.append(SystemProperties.LINE_SEPARATOR);

        return output.toString();
    }

    /**
     * Returns the root cause of the specified exception. If not root cause if found,
     * the specified exception is returned.
     *
     * @param t the exception to find the root cause for.
     * @return the root cause exception.
     */
    public static Throwable getRootCause(Throwable t) {
        // Grabs the root exception. If root is null, then the exception is the parent.
        Throwable rootcauseException = ExceptionUtils.getRootCause(t);
        if (rootcauseException != null) {
            return rootcauseException;
        }
        return t;
    }
}
