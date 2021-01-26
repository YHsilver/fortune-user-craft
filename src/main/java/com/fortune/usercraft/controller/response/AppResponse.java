package com.fortune.usercraft.controller.response;

import com.fortune.usercraft.config.ErrorCode;
import static com.fortune.usercraft.config.ErrorCode.*;

import javax.lang.model.type.NullType;

public class AppResponse<T> {
    private final String ecode;
    private final String msg;
    private final T content;


    public AppResponse(ErrorCode ecode, String msg, T content) {
        this.ecode = ecode.getEcode();
        this.msg = msg;
        this.content = content;
    }

    public static AppResponse<NullType> success(String msg) {
        return new AppResponse<>(SUCCESS, msg, null);
    }

    public static AppResponse<NullType> success() {
        return new AppResponse<>(SUCCESS, null, null);
    }

    public static <T> AppResponse<T> successWithContent(T content) {
        return new AppResponse<>(SUCCESS, null, content);
    }

    public static AppResponse<NullType> failure(ErrorCode ecode, String msg) {
        return new AppResponse<>(ecode, msg, null);
    }

    public String getEcode() {
        return ecode;
    }

    public String getMsg() {
        return msg;
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "ecode='" + ecode + '\'' +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
