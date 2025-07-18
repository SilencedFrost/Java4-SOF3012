package com.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    // Salting cost
    private static final int COST = 12;

    // Return hashed password
    public static String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(COST));
    }

    // Return password verification result
    public static boolean verify(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
