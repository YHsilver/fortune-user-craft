package com.fortune.usercraft.controller;

import com.fortune.usercraft.controller.request.LoginRequest;
import com.fortune.usercraft.controller.request.RegisterRequest;
import com.fortune.usercraft.controller.response.SimpleResponse;
import com.fortune.usercraft.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    // TODO: error handler
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public SimpleResponse register(@RequestBody RegisterRequest request) {
        String uid = userService.registerStudent(request.getPhone(), request.getPassword());
        logger.info("用户注册成功，uid=" + uid);
        // fail
        return SimpleResponse.success("注册成功");
    }

    @PostMapping("/login")
    public SimpleResponse login(@RequestBody LoginRequest request) {
//        String uid = userService.login(request.getPhone(), request.getPassword());
//        logger.info("用户登录成功，uid=" + uid);

        // TODO: set session, and add a filter to set SecurityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        logger.info("===========================================================");
        logger.info("Authentication: " + authentication.toString());
        logger.info("Name:           " + authentication.getName());
        logger.info("Principle:      " + authentication.getPrincipal());
        logger.info("Credentials:    " + authentication.getCredentials());
        logger.info("Details:        " + authentication.getDetails());
        logger.info("Authorities:    " + authentication.getAuthorities());
        logger.info("===========================================================");
//        String uid = userService.login(request.getPhone(), request.getPassword());
//        logger.info("用户登录成功，uid=" + uid);
        return SimpleResponse.success("登录成功");
    }

    @GetMapping("/current")
    public SimpleResponse getCurrentUserInfo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        logger.info("===========================================================");
        logger.info("Authentication: " + authentication.toString());
        logger.info("Name:           " + authentication.getName());
        logger.info("Principle:      " + authentication.getPrincipal());
        logger.info("Credentials:    " + authentication.getCredentials());
        logger.info("Details:        " + authentication.getDetails());
        logger.info("Authorities:    " + authentication.getAuthorities());
        logger.info("===========================================================");
//        String uid = userService.login(request.getPhone(), request.getPassword());
//        logger.info("用户登录成功，uid=" + uid);
        return SimpleResponse.success("success");
    }
}
