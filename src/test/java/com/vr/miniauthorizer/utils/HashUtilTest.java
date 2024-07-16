package com.vr.miniauthorizer.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import resources.fixtures.TestFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashUtilTest {

    @Test
    @DisplayName("Test Hash String")
    void testHashString() {
        String hashedString = HashUtil.hashString(TestFixture.CARD_PASSWORD);
        assertEquals(TestFixture.CARD_PASSWORD_HASH, hashedString);
    }


    @Test
    @DisplayName("Test Compare Hash Success")
    void testCompareHashSuccess() {
        String storedHash = HashUtil.hashString(TestFixture.CARD_PASSWORD_HASH);
        assertTrue(HashUtil.compareHash(TestFixture.CARD_PASSWORD_HASH, storedHash));
    }

    @Test
    @DisplayName("Test Compare Hash Failure")
    void testCompareHashFailure() {
        String storedHash = HashUtil.hashString(TestFixture.CARD_PASSWORD_HASH);
        assertFalse(HashUtil.compareHash(TestFixture.CARD_PASSWORD, storedHash));
    }
}
