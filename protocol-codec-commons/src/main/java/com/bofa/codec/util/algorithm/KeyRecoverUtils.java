package com.bofa.codec.util.algorithm;

import java.security.*;
import java.security.spec.*;

/**
 * @author bofa1ex
 * @since 2020/3/20
 */
public class KeyRecoverUtils {

    public enum KeyRecoverAlgorithm{
        RSA("RSA"),
        DSA("DSA"),
        DH("DH");

        private String algorithm;

        KeyRecoverAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    public static PublicKey recoverPubKey(KeyRecoverAlgorithm keyRecoverAlgo, byte[] pubKeyBytes) {
        try {
            final KeyFactory kf = KeyFactory.getInstance(keyRecoverAlgo.algorithm);
            return kf.generatePublic(new X509EncodedKeySpec(pubKeyBytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static  PrivateKey recoverPrivateKey(KeyRecoverAlgorithm keyRecoverAlgo, byte[] privKeyBytes){
        try{
            final KeyFactory kf = KeyFactory.getInstance(keyRecoverAlgo.algorithm);
            return kf.generatePrivate(new PKCS8EncodedKeySpec(privKeyBytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
