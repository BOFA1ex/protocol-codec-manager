package com.bofa.protocol.codec.util.algorithm;

import com.bofa.protocol.codec.util.ByteUtils;
import org.junit.Test;

/**
 * @author bofa1ex
 * @since 2020/3/20
 */
public class TestAESUtils {

    private byte[] data = new byte[]{0x1, 0x2};
    private final byte[] key = KeyGenUtils.secret_key(KeyGenUtils.KeyGenAlgorithm.AES);

    @Test
    public void aes_ecb() {
        final byte[] encryptBytes = AESUtils.encrypt_ecb(data, key, true);
        final String encryptHex = ByteUtils.bytes2Hex(encryptBytes);
        System.out.println("encryptHex -> " + encryptHex);
        final byte[] decryptBytes = AESUtils.decrypt_ecb(encryptBytes, key, true);
        final String decryptHex = ByteUtils.bytes2Hex(decryptBytes);
        System.out.println("decryptHex -> " + decryptHex);
    }

    @Test
    public void aes_cbc(){
        final byte[] encryptBytes = AESUtils.encrypt_cbc(data, key, true);
        final String encryptHex = ByteUtils.bytes2Hex(encryptBytes);
        System.out.println("encryptHex -> " + encryptHex);
        final byte[] decryptBytes = AESUtils.decrypt_cbc(encryptBytes, key, true);
        final String decryptHex = ByteUtils.bytes2Hex(decryptBytes);
        System.out.println("decryptHex -> " + decryptHex);
    }

}
