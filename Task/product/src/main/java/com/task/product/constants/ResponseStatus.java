package com.task.product.constants;

/**
 * @author Athul KS
 * Enum for standard API response statuses.
 */
public enum ResponseStatus {
    SUCCESS("success"),
    FAIL("fail");

    public final String value;

    ResponseStatus(String value) {
        this.value = value;
    }
}