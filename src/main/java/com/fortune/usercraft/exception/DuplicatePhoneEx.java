package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

public class DuplicatePhoneEx extends AppException {
    public DuplicatePhoneEx() {
        super(ErrorCode.REGISTER_FAIL, "该手机已注册过");
    }
}
