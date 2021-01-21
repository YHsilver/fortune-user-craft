package com.fortune.usercraft.service;

import com.fortune.usercraft.entity.User;
import com.fortune.usercraft.exception.DuplicatePhoneException;
import com.fortune.usercraft.exception.CredentialsMismatchException;
import com.fortune.usercraft.exception.NoSuchUserException;
import com.fortune.usercraft.repository.UserRepository;
import com.fortune.usercraft.util.UserUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String phone, String password) {

        User user = userRepository.findByPhone(phone).orElseThrow(NoSuchUserException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CredentialsMismatchException();
        }
        return user.getUserId();
    }

    public String register(String phone, String password) {
        // don't check the validation of username and password,
        // assume that all the simple checking has been passed

        if (userRepository.existsByPhone(phone)) {
            throw new DuplicatePhoneException();
        }
        String uuid = UserUtil.generateUuid();
        while (userRepository.existsByUserId(uuid)) {
            uuid = UserUtil.generateUuid();
        }
        User user = new User();
        user.setUserId(uuid);
        user.setPhone(phone);
        String encodedPassword = this.passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        user.setRole("STUDENT");

        user = userRepository.save(user);
        return user.getUserId();
    }
}
