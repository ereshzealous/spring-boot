package com.eresh.spring.boot.commons.exception;

import com.eresh.spring.boot.commons.constants.IWSGlobalApiErrorKeys;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
@Getter
@Setter
public class DataValidationException extends ChainedException {
    private static final long serialVersionUID = 4958986506924891491L;
    private String fieldName;
    private Object[] errorKeyParameters;
    private String message;

    public DataValidationException(String fieldName, String errorKey) {
        this(fieldName, errorKey, null);
    }

    public DataValidationException(String fieldName, String errorKey, String message, boolean useMessage) {
        super.setErrorKey(errorKey);
        this.fieldName = fieldName;
        this.message = message;
    }

    public DataValidationException(String fieldName, String errorKey, Object[] errorKeyParameters) {
        this(null, null, fieldName, errorKey, errorKeyParameters);
    }

    public DataValidationException(String message, Throwable e, String fieldName, String errorKey, Object[] errorKeyParameters) {
        super(message, e, errorKey);
        this.fieldName = fieldName;
        this.errorKeyParameters = errorKeyParameters;
    }

    public static DataValidationException getValueRequiredInstance(String field) {
        return new DataValidationException(field, IWSGlobalApiErrorKeys.ERRORS_GENERAL_VALUE_REQUIRED);
    }

    public static DataValidationException getRecordNotFoundInstance(String field) {
        return new DataValidationException(field, IWSGlobalApiErrorKeys.ERRORS_GENERAL_RECORD_NOT_FOUND);
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object[] getErrorKeyParameters() {
        return errorKeyParameters;
    }
    public void setErrorKeyParameters(Object[] errorKeyParameters) {
        this.errorKeyParameters = errorKeyParameters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("fieldName", fieldName)
                .append("errorKey", getErrorKey())
                .append("errorKeyParameters", errorKeyParameters)
                .toString();
    }
}
