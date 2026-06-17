package com.sgic.Expense.Tracker.enums;

import lombok.Getter;

@Getter
public enum RestApiResponseStatusCodes {

    // =============================
    // 2xxx — Success
    // =============================
    OK(2000, "Success"),
    CREATED(2001, "Created"),
    ACCEPTED(2002, "Accepted"),
    NO_CONTENT(2004, "No Content"),
    PARTIAL_CONTENT(2006, "Partial Content"),

    // =============================
    // 4xxx — Client Errors
    // =============================
    BAD_REQUEST(4000, "Bad Request"),
    UNAUTHORIZED(4001, "Unauthorized"),
    FORBIDDEN(4003, "Forbidden"),
    NOT_FOUND(4004, "Not Found"),
    METHOD_NOT_ALLOWED(4005, "Method Not Allowed"),
    CONFLICT(4009, "Conflict"),
    GONE(4010, "Resource No Longer Available"),
    UNSUPPORTED_MEDIA_TYPE(4015, "Unsupported Media Type"),
    UNPROCESSABLE_ENTITY(4022, "Unprocessable Entity"),

    // Custom client errors
    INVALID_PAYLOAD(4060, "Invalid Payload"),
    INVALID_CREDENTIALS(4061, "Invalid Credentials"),
    TOKEN_EXPIRED(4062, "Token Expired"),
    ACCESS_REVOKED(4063, "Access Revoked"),
    DUPLICATE_ENTRY(4064, "Duplicate Entry"),
    REQUIRED_FIELD_MISSING(4065, "Required Field Missing"),
    VALIDATION_FAILED(4066, "Validation Failed"),
    INVALID_STATE(4067, "Invalid State");


    private final int code;
    private final String message;

    RestApiResponseStatusCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
