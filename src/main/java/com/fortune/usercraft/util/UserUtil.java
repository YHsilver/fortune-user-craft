package com.fortune.usercraft.util;

import java.util.UUID;

public class UserUtil {
    private UserUtil() {
        // not instantiable
    }
    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
