package com.fortune.usercraft.exception;

public class DuplicatePhoneException extends AppException {
    public DuplicatePhoneException() {
        super("0001", "电话号码重复");
    }
}
