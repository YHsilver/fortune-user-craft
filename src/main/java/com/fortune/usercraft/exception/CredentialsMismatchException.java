package com.fortune.usercraft.exception;

public class CredentialsMismatchException extends AppException {
    public CredentialsMismatchException() {
        super("0003", "手机号码或密码错误"); // todo: 这里的code要可配置
    }
}
