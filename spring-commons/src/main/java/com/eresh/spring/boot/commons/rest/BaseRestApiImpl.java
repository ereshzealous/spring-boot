package com.eresh.spring.boot.commons.rest;

import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.eresh.spring.boot.commons.exception.DataValidationException;
import com.eresh.spring.boot.commons.exception.ExceptionLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class BaseRestApiImpl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected <I> ResponseEntity<RestResponse> inboundServiceCall(I request, IServiceMethod<I, ResponseEntity<RestResponse>> service) throws RestApiException {
        try {
            ResponseEntity<RestResponse> response = service.execute(request);
            return response;
        } catch (Exception e) {
            logger.error("Error processing the " + e);
            throw processException(e);
        }
    }

    protected RestApiException processException(Exception e) {
        logger.debug("At: processException()");
        RestApiException restApiException = null;
        Throwable rootCause = ExceptionLogUtil.getRootCause(e);
        if (e instanceof DataValidationException) {
            restApiException = new RestApiException((DataValidationException) e);
        } else if (rootCause instanceof DataValidationException) {
            restApiException = new RestApiException((DataValidationException) rootCause);
        } else if (e instanceof ApplicationException) {
            restApiException = new RestApiException(e.getMessage(), RestApiException.createApplicationExceptionFault((ApplicationException) e));
        }

        if (restApiException == null) {
            restApiException = new RestApiException(e.getMessage(), RestApiException.createUnexpectedFault(e));
        }
        return restApiException;
    }
}
