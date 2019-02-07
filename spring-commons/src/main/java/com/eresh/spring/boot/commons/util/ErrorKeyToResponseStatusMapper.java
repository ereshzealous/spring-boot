package com.eresh.spring.boot.commons.util;

import com.eresh.spring.boot.commons.constants.IWSGlobalApiErrorKeys;
import com.eresh.spring.boot.commons.rest.IResponseCodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author Eresh Gorantla on 28-Mar-2018
 */

public class ErrorKeyToResponseStatusMapper {
private static final Map<String, ResponseStatus> MAPPER = new HashMap<String, ResponseStatus>();
	
	private ErrorKeyToResponseStatusMapper() {
		//Prevent instantiation.
	}
	
	static {
		// 404:Not Found error keys.
		MAPPER.put(IWSGlobalApiErrorKeys.ERRORS_PUBLIC_RECORD_NOT_FOUND, ResponseStatus.NOT_FOUND);

		// 401:Unauthorized error keys.
		MAPPER.put(IWSGlobalApiErrorKeys.ERRORS_AUTHENTICATION_FAILED, ResponseStatus.UNAUTHENTICATED);

		// 500:Internal Server Error keys.
		MAPPER.put(IWSGlobalApiErrorKeys.ERRORS_GENERAL_ERROR_UNEXPECTED, ResponseStatus.INTERNAL_SERVER_ERROR);

		// 503: Service Unavailable
		MAPPER.put(IWSGlobalApiErrorKeys.ERRORS_GENERAL_SERVICE_UNAVAILABLE, ResponseStatus.SERVICE_UNAVAILABLE);
		
		// 401:Unauthorized error keys.
        MAPPER.put(IWSGlobalApiErrorKeys.ERRORS_AUTHORIZATION_FAILED, ResponseStatus.UNAUTHORIZED);
	}
	
	/**
	 * Returns the response status for the passed error key. If no specific response status is found,
	 * returns 400 status code with DatavalidationError result. if error key is blank or null, returns 
	 * 500 status code with UnexpectedSystemError result.
	 * @param errorKey
	 * @return
	 */
	public static ResponseStatus getResponseStatus(String errorKey) {
		if(StringUtils.isBlank(errorKey)) { //If errorKey is blank, return 500: Internal Server Error.
			return ResponseStatus.INTERNAL_SERVER_ERROR;
		}
		return MAPPER.get(errorKey) != null ? MAPPER.get(errorKey) : ResponseStatus.BAD_REQUEST;
	}
	
	public static final class ResponseStatus {
		public static final ResponseStatus INTERNAL_SERVER_ERROR = new ResponseStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR, IResponseCodes.UNEXPECTED_SYSTEM_ERROR);
		public static final ResponseStatus NOT_FOUND = new ResponseStatus(HttpStatus.SC_NOT_FOUND, IResponseCodes.DATA_VALIDATION_ERROR);
		public static final ResponseStatus BAD_REQUEST = new ResponseStatus(HttpStatus.SC_BAD_REQUEST, IResponseCodes.DATA_VALIDATION_ERROR);
		public static final ResponseStatus UNAUTHENTICATED = new ResponseStatus(HttpStatus.SC_UNAUTHORIZED, IResponseCodes.AUTHENTICATION_FAILED);
		public static final ResponseStatus UNAUTHORIZED = new ResponseStatus(HttpStatus.SC_FORBIDDEN, IResponseCodes.AUTHORIZATION_FAILED);
		public static final ResponseStatus SERVICE_UNAVAILABLE = new ResponseStatus(HttpStatus.SC_SERVICE_UNAVAILABLE, IResponseCodes.SERVICE_UNAVAILABLE);
		
		private int status;
		private String result;
		
		ResponseStatus(int status, String result) {
			this.status = status;
			this.result = result;
		}

		public int getStatus() {
			return status;
		}
		
		public String getResult() {
			return result;
		}
	}
}


