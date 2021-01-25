package com.fortune.usercraft.service;

import com.fortune.usercraft.entity.UserCore;
import com.fortune.usercraft.exception.DuplicatePhoneException;
import com.fortune.usercraft.repository.UserCoreRepo;
import com.fortune.usercraft.util.UserUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserCoreRepo userCoreRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserCoreRepo userCoreRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userCoreRepo = userCoreRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String registerStudent(String phone, String password) {
        // don't check the validation of username and password,
        // assume that all the simple checking has been passed
        if (userCoreRepo.existsByUsername(phone)) {
            throw new DuplicatePhoneException();
        }
        String uuid = UserUtil.generateUuid();
        while (userCoreRepo.existsByUserId(uuid)) {
            uuid = UserUtil.generateUuid();
        }
        UserCore userCore = new UserCore();
        userCore.setUserId(uuid);
        userCore.setUsername(phone);
        String encodedPassword = this.passwordEncoder.encode(password);
        userCore.setPassword(encodedPassword);

        userCore.setRole("STUDENT");

        userCore = userCoreRepo.save(userCore);
        return userCore.getUserId();
    }
}
