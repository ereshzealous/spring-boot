package com.eresh.spring.boot.commons.util;

import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.eresh.spring.boot.commons.exception.DataValidationException;
import com.eresh.spring.boot.commons.exception.ExceptionLogUtil;
import com.eresh.spring.boot.commons.rest.RestApiException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
public class RestApiExceptionGenerator {
    public static RestApiException generateRestApiException(Exception ex) {
        RestApiException restApiException = null;
        Throwable rootCause = ExceptionLogUtil.getRootCause(ex);

        if (rootCause instanceof RestApiException) {
            restApiException = (RestApiException) rootCause;
        } else if (rootCause instanceof DataValidationException) {
            restApiException = new RestApiException((DataValidationException) rootCause);
        } else if (rootCause instanceof ApplicationException) {
            restApiException = new RestApiException(rootCause.getMessage(), RestApiException.createApplicationExceptionFault((ApplicationException) rootCause));
        } else if (rootCause instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException exception = (MissingServletRequestParameterException) rootCause;
            DataValidationException dve = new DataValidationException(exception.getParameterName(), exception.getMessage());
            restApiException = new RestApiException((DataValidationException) dve);
        }
        if (restApiException == null) {
            restApiException = new RestApiException(ex.getMessage(), RestApiException.createUnexpectedFault(new Exception(ex)));
        }
        return  restApiException;
    }
}
