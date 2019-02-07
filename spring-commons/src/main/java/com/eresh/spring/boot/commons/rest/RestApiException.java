package com.eresh.spring.boot.commons.rest;

import com.eresh.spring.boot.commons.constants.IWSGlobalApiErrorKeys;
import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.eresh.spring.boot.commons.exception.DataValidationException;
import com.eresh.spring.boot.commons.exception.ExceptionLogUtil;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class RestApiException extends Exception {
    private RestFault fault;

    public RestApiException() {
        super();
    }

    public RestApiException(DataValidationException dve) {
        this.fault = createFault(dve);
    }

    public RestApiException(String message, RestFault wsFault) {
        super(message);
        Validate.notNull(wsFault, "Fault cannot be null.");
        this.fault = wsFault;
    }

    public RestFault getFaultInfo() {
        return this.fault;
    }

    public static RestFault createApplicationExceptionFault(ApplicationException ae) {
        RestFault fault = new RestFault();
        fault.setErrorKey(ae.getErrorKey());
        fault.setErrorMessage(ae.getMessage());
        fault.setResult(ae.getResult());
        return fault;
    }

    public static RestFault createUnexpectedFault(Exception ex) {
        RestFault fault = new RestFault();
        fault.setResult(IResponseCodes.UNEXPECTED_SYSTEM_ERROR);
        fault.setErrorKey(IWSGlobalApiErrorKeys.ERRORS_GENERAL_ERROR_UNEXPECTED);
        fault.setErrorMessage(ExceptionLogUtil.getRootCause(ex).getMessage());
        fault.setUid(UUID.randomUUID().toString());

        return fault;
    }

    private RestFault createFault(DataValidationException e) {
        RestFault fault = new RestFault();
        fault.setFieldName(e.getFieldName());
        fault.setErrorKey(e.getErrorKey());
        fault.setErrorMessage(e.getMessage());
        fault.setResult(IResponseCodes.DATA_VALIDATION_ERROR);
        return fault;
    }
}
