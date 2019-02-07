package com.eresh.spring.boot.commons.constants;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public interface IWSGlobalApiErrorKeys extends IGlobalErrorKeys {
    String ERRORS_AUTHENTICATION_FAILED = "errors.authentication.failed";
    String ERRORS_PUBLIC_RECORD_NOT_FOUND = "errors.general.record.not.found";
    String ERRORS_GENERAL_RECORD_NOT_FOUND = "errors.exception.record.not.found";
    String ERRORS_AUTHORIZATION_FAILED = "errors.authorization.failed";
    String ERRORS_INACTIVE = "errors.inactive.state";
    String ERRORS_ALREADY_EXISTS = "errors.already.exists";
}
