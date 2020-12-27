package com.fortune.usercraft.exception;

public class DuplicatePhoneException extends AppException {
    public DuplicatePhoneException() {
        super("0001", "该手机已注册过");
    }
}
