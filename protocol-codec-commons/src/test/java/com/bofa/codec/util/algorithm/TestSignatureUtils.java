package com.bofa.codec.util.algorithm;

import org.junit.Test;

import java.security.KeyPair;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestSignatureUtils {

    @Test
    public void sign(){
        final byte[] data = {0x1, 0x2};
        final KeyPair keyPair = KeyPairGenUtils.generateKeyPair(KeyPairGenUtils.KeyPairAlgorithm.DSA);
        final byte[] signd = SignatureUtils.encrypt(SignatureUtils.SignatureAlgorithm.SHA1withDSA, data, keyPair.getPrivate());
        final boolean valid = SignatureUtils.decryptAndVerify(SignatureUtils.SignatureAlgorithm.SHA1withDSA, data, signd, keyPair.getPublic());
        System.out.println("valid? -> " + valid);
    }
}
