package com.bofa.codec.util.algorithm;

import com.bofa.codec.util.ByteUtils;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PublicKey;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestRSAUtils {

    @Test
    public void rsa_encrypt() {
        final byte[] data = {0x1, 0x2};
        final KeyPair rsaKeyPair = KeyPairGenUtils.generateKeyPair(KeyPairGenUtils.KeyPairAlgorithm.RSA);

        final byte[] pubKeyBytes = rsaKeyPair.getPublic().getEncoded();
        final PublicKey publicKey = KeyRecoverUtils.recoverPubKey(KeyRecoverUtils.KeyRecoverAlgorithm.RSA, pubKeyBytes);
        final byte[] encryptBytes = RSAUtils.encrypt(data, publicKey);

        final String encryptHex = ByteUtils.bytes2Hex(encryptBytes);
        System.out.println("encryptHex -> " + encryptHex);
        final byte[] decryptBytes = RSAUtils.decrypt(encryptBytes, rsaKeyPair.getPrivate());
        final String decryptHex = ByteUtils.bytes2Hex(decryptBytes);
        System.out.println("decryptHex -> " + decryptHex);
    }
}
