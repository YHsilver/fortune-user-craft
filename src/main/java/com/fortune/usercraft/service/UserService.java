package com.fortune.usercraft.service;

import com.fortune.usercraft.entity.UserCore;
import com.fortune.usercraft.exception.DuplicatePhoneEx;
import com.fortune.usercraft.exception.UnknownEx;
import com.fortune.usercraft.repository.UserCoreRepo;
import com.fortune.usercraft.util.UserUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserCoreRepo userCoreRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserCoreRepo userCoreRepo,
                       PasswordEncoder passwordEncoder) {
        this.userCoreRepo = userCoreRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerStudent(String phone, String password) {
        // don't check the validation of username and password,
        // assume that all the simple checking has been passed
        if (userCoreRepo.existsByUsername(phone)) {
            throw new DuplicatePhoneEx();
        }
        String uuid = UserUtil.generateUuid();
        UserCore userCore = new UserCore();
        userCore.setUserId(uuid);
        userCore.setUsername(phone);
        String encodedPassword = this.passwordEncoder.encode(password);
        userCore.setPassword(encodedPassword);

        userCore.setRole("STUDENT");

        userCore = userCoreRepo.save(userCore);
        return userCore.getUserId();
    }

    public UserCore getUserInfoByUid(String uid) {
        return userCoreRepo.findByUserId(uid).orElseThrow(UnknownEx::new);
    }
}
