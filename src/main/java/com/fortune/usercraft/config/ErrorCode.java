package com.fortune.usercraft.config;

public enum ErrorCode {
    SUCCESS("0"),
    UNKNOWN("-1"),
    UNAUTHENTICATED("1001"),
    NOT_FOUND("1002"),
    UNAUTHORIZED("1003"),
    INVALID_ARGUMENT("1004"),
    REGISTER_FAIL("1005");

    private final String ecode;

    ErrorCode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
    }
}
