package com.fortune.usercraft.security;

import com.fortune.usercraft.entity.UserCore;
import com.fortune.usercraft.repository.UserCoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Supplier;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCoreRepo userCoreRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserCore userCore = userCoreRepo.findByUsername(username).orElseThrow(
                (Supplier<AuthenticationException>) () -> new UsernameNotFoundException("用户名密码不匹配"));
        UserDetails user = User.builder()
                    .username(username)
                    .password(userCore.getPassword())
                    .roles(userCore.getRole())
                    .build();
        AppUserDetails appUserDetails = new AppUserDetails(userCore.getUserId(), userCore.getRole(),
                user.getUsername(), user.getPassword(), user.getAuthorities());
        return appUserDetails;
    }
}
