package com.bofa.codec.util.algorithm;

import com.bofa.codec.util.ByteUtils;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestPBEUtils {

    @Test
    public void pbe_decrypt() throws NoSuchAlgorithmException {
        String cmd = "hello world";
        byte[] data = new byte[]{0x1, 0x2};
        final byte[] salt = RandomGenUtils.salt(16);
        final byte[] encryptBytes = PBEUtils.encrypt(cmd, salt, data, 5);
        final String encryptHex = ByteUtils.bytes2Hex(encryptBytes);
        System.out.println("encryptHex -> " + encryptHex);
        final byte[] decryptBytes = PBEUtils.decrypt(cmd, salt, encryptBytes, 5);
        final String decryptHex = ByteUtils.bytes2Hex(decryptBytes);
        System.out.println("decryptHex -> " + decryptHex);
    }
}
