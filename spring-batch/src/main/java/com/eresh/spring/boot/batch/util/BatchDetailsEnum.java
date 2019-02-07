package com.eresh.spring.boot.batch.util;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
public enum BatchDetailsEnum {
    INITIATED(Constants.INITIATED),
    COMPLETED(Constants.COMPLETED),
    FAILED(Constants.FAILED);

    private String value;
    BatchDetailsEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public interface Constants {
        String INITIATED = "Initiated";
        String COMPLETED = "Completed";
        String FAILED = "Failed";
    }
}
