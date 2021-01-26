package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

public class DuplicateUsernameEx extends AppException {
    public DuplicateUsernameEx() {
        super(ErrorCode.REGISTER_FAIL, "该用户名与其他用户名/手机号重复");
    }
}
