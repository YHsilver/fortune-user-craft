package com.fortune.usercraft.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {
    private final String uid;
    private final String role;
    public AppUserDetails(String uid, String role,
                          String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.uid = uid;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "MyUserDetails{" +
                "uid='" + uid + '\'' +
                ", username='" + getUsername() + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
