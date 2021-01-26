package com.fortune.usercraft.controller;

import com.fortune.usercraft.controller.request.StudentRegisterRequest;
import com.fortune.usercraft.controller.response.AppResponse;
import com.fortune.usercraft.entity.UserCore;
import com.fortune.usercraft.exception.NotLoginEx;
import com.fortune.usercraft.security.AppUserDetails;
import com.fortune.usercraft.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.lang.model.type.NullType;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AppResponse<NullType> registerStudent(@RequestBody @Validated StudentRegisterRequest request) {
        String uid = userService.registerStudent(request.getPhone(), request.getPassword());
        logger.info("用户注册成功，uid=" + uid);
        return AppResponse.success("注册成功");
    }

    @GetMapping("/current")
    public AppResponse<UserCore> getCurrentUserInfo(@AuthenticationPrincipal AppUserDetails userDetails) {
        UserCore current = userService.getUserInfoByUid(userDetails.getUid());
        return AppResponse.successWithContent(current);
    }

    @GetMapping("/only_leader")
    @RolesAllowed("LEADER")
    public AppResponse<NullType> fuck() {
        // TODO: 该接口只用于演示
        return AppResponse.success("你是领导，访问成功！");
    }
}
