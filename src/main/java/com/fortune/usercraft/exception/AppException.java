package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

/**
 * Base Exception of this application, all the exceptions must inherent this.
 */
public class AppException extends RuntimeException {
    private ErrorCode ecode;
    private String message;

    AppException(ErrorCode ecode, String message) {
        this.ecode = ecode;
        this.message = message;
    }

    public ErrorCode getEcode() {
        return ecode;
    }

    public void setEcode(ErrorCode ecode) {
        this.ecode = ecode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
