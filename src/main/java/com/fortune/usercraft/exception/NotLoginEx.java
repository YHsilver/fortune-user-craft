package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

public class NotLoginEx extends AppException {
    public NotLoginEx() {
        super(ErrorCode.UNAUTHENTICATED, "用户未登录");
    }
}
