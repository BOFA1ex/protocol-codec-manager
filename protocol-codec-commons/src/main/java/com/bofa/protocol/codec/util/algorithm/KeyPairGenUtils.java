package com.bofa.protocol.codec.util.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;

/**
 * @author bofa1ex
 * @since 2020/3/20
 */
public class KeyPairGenUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public enum KeyPairAlgorithm{
        DH("DH"),
        DSA("DSA"),
        RSA("RSA"),
        ECDSA("ECDSA");

        private String algorithm;

        KeyPairAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    public static KeyPair generateKeyPair(KeyPairAlgorithm keyPairAlgo){
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(keyPairAlgo.algorithm);
            kpGen.initialize(2 << 9);
            return kpGen.generateKeyPair();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
