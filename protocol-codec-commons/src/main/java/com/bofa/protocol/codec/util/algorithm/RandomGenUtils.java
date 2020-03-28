package com.bofa.protocol.codec.util.algorithm;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class RandomGenUtils {

    public static byte[] salt(int numBytes) {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            return sr.generateSeed(numBytes);
        } catch (NoSuchAlgorithmException ignore) {
        }
        return null;
    }
}
