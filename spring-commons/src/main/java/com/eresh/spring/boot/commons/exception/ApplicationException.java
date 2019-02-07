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
public class ApplicationException extends ChainedException {
    private String result = "Application Error";
    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Exception exc) {
        this(msg, exc, null);
    }

    public ApplicationException(Exception exc) {
        this("AppException", exc, null);

        if (exc instanceof ChainedException) {
            this.setErrorKey(((ChainedException) exc).getErrorKey());
        }
    }

    public ApplicationException() {
        this("System Exception", null, null);
    }

    public ApplicationException(String message, Throwable e) {
        this(message, e, null);
    }

    public ApplicationException(Throwable e) {
        this(e.getMessage(), e);

        if (e instanceof ChainedException) {
            this.setErrorKey(((ChainedException) e).getErrorKey());
        }
    }

    public ApplicationException(String message, Throwable e, String errorKey) {
        this(message, e, errorKey, null);
    }

    public ApplicationException(String message, Throwable e, String errorKey, Object[] errorParams) {
        super(message, e, errorKey, errorParams);

        if (e instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException) e;
        }
    }

    public boolean equals(final Object other) {
        if (!(other instanceof ApplicationException)) {
            return false;
        }
        ApplicationException castOther = (ApplicationException) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
