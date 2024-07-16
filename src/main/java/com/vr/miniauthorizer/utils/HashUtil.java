package com.vr.miniauthorizer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {

    private static final String SHA_256 = "SHA-256";

    private HashUtil() {
    }

    /**
     * Hashes the input string using SHA-256 algorithm and returns the Base64-encoded hash.
     *
     * @param input The input string to be hashed.
     * @return The Base64-encoded hash of the input string.
     * @throws RuntimeException If there is an error while hashing the input string.
     */
    public static String hashString(final String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] encodedHash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(ExceptionMessages.ERROR_WHILE_HASHING_STRING, e);
        }
    }

    /**
     * Compares a plain text input string with a stored hash to verify if they match.
     *
     * @param input      The plain text input string to be hashed and compared.
     * @param storedHash The stored hash against which the input is compared.
     * @return true if the input string hashes to the same value as the stored hash, false otherwise.
     */
    public static boolean compareHash(final String input, final String storedHash) {
        String hashedInput = hashString(input);
        return hashedInput.equals(storedHash);
    }
}
