package com.fortune.usercraft.controller.response;

public class SimpleResponse {
    private String ecode;
    private String msg;
    // TODO: add a content

    public SimpleResponse(String ecode, String msg) {
        this.ecode = ecode;
        this.msg = msg;
    }

    public static SimpleResponse success(String msg) {
        return new SimpleResponse("0", msg);
    }

    public static SimpleResponse success() {
        return new SimpleResponse("0", null);
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
