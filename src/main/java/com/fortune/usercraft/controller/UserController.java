package com.fortune.usercraft.controller;

import com.fortune.usercraft.controller.request.LeaderRegisterRequest;
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

    @PostMapping("/registerStudent")
    public AppResponse<NullType> registerStudent(@RequestBody @Validated StudentRegisterRequest request) {
        String uid = userService.registerStudent(request.getPhone(), request.getPassword());
        logger.info("学员注册成功，uid=" + uid);
        return AppResponse.success("注册成功");
    }

    @PostMapping("/registerLeader")
    public AppResponse<NullType> registerLeader(@RequestBody @Validated LeaderRegisterRequest request) {
        String uid = userService.registerLeader(request.getUsername(), request.getPassword());
        logger.info("领导注册成功，uid=" + uid);
        return AppResponse.success("注册成功");
    }

    @GetMapping("/current")
    public AppResponse<UserCore> getCurrentUserInfo(@AuthenticationPrincipal AppUserDetails currentUser) {
        if (currentUser == null) {
            throw new NotLoginEx(); // 该方法被配置为可被未登录用户访问，所以才可能为空。默认所有其他方法都只能登录后访问。
        }
        UserCore current = userService.getUserInfoByUid(currentUser.getUid());
        return AppResponse.successWithContent(current);
    }

    @GetMapping("/only_leader")
    @RolesAllowed("LEADER")
    public AppResponse<NullType> onlyLeaderGetThis() {
        return AppResponse.success("你是领导，访问成功！");
    }
}
