package com.eresh.spring.boot.commons.util;

import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.eresh.spring.boot.commons.exception.DataValidationException;
import com.eresh.spring.boot.commons.exception.ExceptionLogUtil;
import com.eresh.spring.boot.commons.exception.UnexpectedException;
import com.eresh.spring.boot.commons.rest.RestApiException;
import com.eresh.spring.boot.commons.rest.RestFault;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@ControllerAdvice
public class ExceptionTranslator {
    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper mapper;

    @ExceptionHandler(value = {RestApiException.class})
    protected ResponseEntity<Object> handleException(RestApiException ex) throws Exception {
        RestApiException restApiException = null;
        Throwable rootCause = ExceptionLogUtil.getRootCause(ex);

        if (rootCause instanceof RestApiException) {
            restApiException = (RestApiException) rootCause;
        } else if (rootCause instanceof DataValidationException) {
            restApiException = new RestApiException((DataValidationException) rootCause);
        } else if (rootCause instanceof ApplicationException) {
            restApiException = new RestApiException(rootCause.getMessage(), RestApiException.createApplicationExceptionFault((ApplicationException) rootCause));
        }
        // Exception is unknown.
        if (restApiException == null) {
            restApiException = new RestApiException(ex.getMessage(), RestApiException.createUnexpectedFault(new Exception(ex)));
        }
        RestFault fault = restApiException.getFaultInfo();

        // Map error key into status.
        int status = ErrorKeyToResponseStatusMapper.getResponseStatus(fault.getErrorKey()).getStatus();
        fault.setUid(UUID.randomUUID().toString());
        // Add content type and x-cub-hdr to Http headers.
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("error_hdr", toJson(fault));
        // Return the response entity with fault response, headers and status.
        return new ResponseEntity<Object>(fault, headers, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleRootException(Exception ex) throws Exception {
        RestApiException restApiException = RestApiExceptionGenerator.generateRestApiException(ex);
        RestFault fault = restApiException.getFaultInfo();

        // Map error key into status.
        int status = ErrorKeyToResponseStatusMapper.getResponseStatus(fault.getErrorKey()).getStatus();
        fault.setUid(UUID.randomUUID().toString());
        // Add content type and x-cub-hdr to Http headers.
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("error_hdr", toJson(fault));
        // Return the response entity with fault response, headers and status.
        return new ResponseEntity<Object>(fault, headers, HttpStatus.valueOf(status));
    }

    public String toJson(Object entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new UnexpectedException("Error converting entity to JSON string", e);
        }
    }
}
