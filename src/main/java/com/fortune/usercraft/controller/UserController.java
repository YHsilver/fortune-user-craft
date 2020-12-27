package com.fortune.usercraft.controller;

import com.fortune.usercraft.controller.request.LoginRequest;
import com.fortune.usercraft.controller.request.RegisterRequest;
import com.fortune.usercraft.controller.response.SimpleResponse;
import com.fortune.usercraft.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public SimpleResponse register(@RequestBody RegisterRequest request) {
        String uid = userService.register(request.getPhone(), request.getPassword());
        logger.info("用户注册成功，uid=" + uid);
        return SimpleResponse.success("注册成功");
    }

    @PostMapping("/login")
    public SimpleResponse login(@RequestBody LoginRequest request) {
        String uid = userService.login(request.getPhone(), request.getPassword());
        logger.info("用户登录成功，uid=" + uid);
        return SimpleResponse.success("登录成功");
    }
}
