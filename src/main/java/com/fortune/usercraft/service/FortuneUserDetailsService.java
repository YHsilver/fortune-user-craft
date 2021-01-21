package com.fortune.usercraft.service;

import com.fortune.usercraft.entity.User;
import com.fortune.usercraft.exception.NoSuchUserException;
import com.fortune.usercraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class FortuneUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        return loadUserByPhone(s);
    }

    public UserDetails loadUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone).orElseThrow(NoSuchUserException::new);
        // todo: finish it.
        // mark: 对资源的权限每次查询数据库鉴权，
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(phone)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        return userDetails;
    }
}
