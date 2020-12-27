package com.fortune.usercraft.exception;

public class NoSuchUserException extends AppException {

    public NoSuchUserException() {
        super("0002", "该用户不存在");
    }
}
