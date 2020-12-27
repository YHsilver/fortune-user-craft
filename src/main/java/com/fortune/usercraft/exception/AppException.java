package com.fortune.usercraft.exception;

/**
 * Base Exception of this application, all the exceptions must inherent this.
 */
public class AppException extends RuntimeException {
    private String code;
    private String message;

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
