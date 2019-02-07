package com.eresh.spring.boot.commons.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
@JsonPropertyOrder(value={"result", "uid", "fieldName", "errorKey", "errorMessage", "params"})
public class RestFault {
    private String result;
    private String uid;
    private String fieldName;
    private String errorKey;
    private String errorMessage;
    private List<String> params;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorKey() {
        return errorKey;
    }
    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }
    public List<String> getParams() {
        return params;
    }
    public void setParams(List<String> params) {
        this.params = params;
    }
}
