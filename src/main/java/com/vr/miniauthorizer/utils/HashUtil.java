package com.vr.miniauthorizer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {

    private static final String SHA_256 = "SHA-256";

    private HashUtil() {
    }

    public static String hashString(final String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] encodedHash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(ExceptionMessages.ERROR_WHILE_HASHING_STRING, e);
        }
    }

    public static boolean compareHash(final String input, final String storedHash) {
        String hashedInput = hashString(input);
        return hashedInput.equals(storedHash);
    }
}
