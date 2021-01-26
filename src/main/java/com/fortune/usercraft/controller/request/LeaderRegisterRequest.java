package com.fortune.usercraft.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LeaderRegisterRequest {
    @Pattern(regexp = "^[a-zA-z]\\w{2,9}$", message = "用户名应以字母开头，字母、数字或下划线组成，长3-10个字符")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "密码应当由6-20位字母数字组成")
    @NotBlank(message = "密码不能为空")
    private String password;

    public LeaderRegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
