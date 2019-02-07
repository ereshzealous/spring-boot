package com.eresh.spring.boot.commons.rest;

/**
 * Created by @author Eresh Gorantla on 28-Mar-2018
 */

public interface IAPIResponseCode {
	public static final Integer SUCCESS = 0;
	public static final Integer AUTHENTICATION_FAILED = 1;
	public static final Integer PARAMETER_VALIDATION_FAILED = 2;
	public static final Integer UNEXPECTED_SYSTEM_ERROR = 3;
	public static final Integer FAILED = 4;
}


