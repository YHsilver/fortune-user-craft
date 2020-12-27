package com.fortune.usercraft.service;

import com.fortune.usercraft.entity.User;
import com.fortune.usercraft.exception.DuplicatePhoneException;
import com.fortune.usercraft.exception.MismatchPhoneAndPasswordException;
import com.fortune.usercraft.exception.NoSuchUserException;
import com.fortune.usercraft.repository.UserRepository;
import com.fortune.usercraft.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.function.SupplierUtils;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String phone, String password) {
        User user = userRepository.findByPhone(phone).orElseThrow(NoSuchUserException::new);
        if (!UserUtil.matchPassword(password, user.getPassword())) {
            throw new MismatchPhoneAndPasswordException();
        }
        return user.getUid();
    }

    public String register(String phone, String password) {
        // don't check the validation of username and password,
        // assume that all the simple checking has been passed

        if (userRepository.existsByPhone(phone)) {
            throw new DuplicatePhoneException();
        }
        String uuid = UserUtil.generateUuid();
        while (userRepository.existsByUid(uuid)) {
            uuid = UserUtil.generateUuid();
        }
        User user = new User();
        user.setUsername(phone);
        user.setPhone(phone);
        String encodedPassword = UserUtil.encodePassword(password);
        user.setPassword(encodedPassword);

        user = userRepository.save(user);
        return user.getUid();
    }
}
