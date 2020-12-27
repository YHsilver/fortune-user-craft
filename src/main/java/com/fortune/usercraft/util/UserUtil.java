package com.fortune.usercraft.util;


import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserUtil {
    private UserUtil() {
        // not instantiable
    }
    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static String encodePassword(String password) {
        PasswordEncoder encoder = getPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean matchPassword(String raw, String encoded) {
        PasswordEncoder encoder = getPasswordEncoder();
        return encoder.matches(raw, encoded);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
