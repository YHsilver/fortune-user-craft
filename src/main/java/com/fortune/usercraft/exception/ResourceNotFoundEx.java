package com.fortune.usercraft.exception;

import com.fortune.usercraft.config.ErrorCode;

public class ResourceNotFoundEx extends AppException {
    ResourceNotFoundEx() {
        super(ErrorCode.NOT_FOUND, "未找到该资源");
    }

    ResourceNotFoundEx(String msg) {
        super(ErrorCode.NOT_FOUND, msg);
    }
}
