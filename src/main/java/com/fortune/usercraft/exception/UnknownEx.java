package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

public class UnknownEx extends AppException {
    public UnknownEx() {
        super(ErrorCode.UNKNOWN, "未知错误");
    }
}
