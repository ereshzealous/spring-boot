package com.eresh.spring.boot.commons.rest;

/**
 * Created by @author Eresh Gorantla on 28-Mar-2018
 */

@FunctionalInterface
public interface IServiceMethod<I,O> {
    O execute(I request) throws Exception;
}


