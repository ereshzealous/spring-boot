package com.eresh.spring.boot.commons.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
@Getter
@Setter
public class ChainedException extends Exception {
    private String errorKey;
    private Object[] errorParams;
    private String uniqueId;

    public ChainedException() {
        super();
        process(null, null);
    }

    public ChainedException(String message) {
        super(message);
        process(null, null);
    }

    public ChainedException(String message, Throwable cause) {
        super(message, cause);
        process(cause, null);
    }

    public ChainedException(Throwable cause) {
        super(cause);
        process(cause, null);
    }

    public ChainedException(String message, Throwable cause, String errKey) {
        super(message, cause);
        process(cause, errKey);
    }

    public ChainedException(String message, Throwable cause, String errKey, Object[] errParams) {
        super(message, cause);
        process(cause, errKey, errParams);
    }

    private void process(Throwable cause, String errKey) {
        process(cause, errKey, null);
    }

    private void process(Throwable cause, String errKey, Object[] errParams) {
        setErrorKey(errKey);
        setErrorParams(errParams);
        // always generate a unique id for each exception. DO NOT reuse it from the earlier exception
        // which causes the ExceptionUtils.getRootCause() not get the correct root cause.
        setUniqueId(ExceptionIDGenerator.getExceptionID());
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(super.toString())
                .append("errorKey", errorKey)
                .append("uniqueId", uniqueId)
                .toString();
    }

    public boolean equals(final Object other) {
        if (!(other instanceof ChainedException)) {
            return false;
        }
        ChainedException castOther = (ChainedException) other;
        return new EqualsBuilder()
                .append(errorKey, castOther.errorKey)
                .append(uniqueId, castOther.uniqueId)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(errorKey)
                .append(uniqueId)
                .toHashCode();
    }
}
