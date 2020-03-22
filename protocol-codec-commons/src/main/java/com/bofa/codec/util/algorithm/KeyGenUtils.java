package com.bofa.codec.util.algorithm;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author bofa1ex
 * @description 对称密钥生成工具类
 * @since 2020/3/20
 */
public class KeyGenUtils {

    public enum KeyGenAlgorithm {
        AES("AES"),
        DES("DES"),
        HmacMD5("HmacMD5"),
        HmacSHA1("HmacSHA1"),
        HmacSHA224("HmacSHA224"),
        HmacSHA256("HmacSHA256"),
        ChaCha20("ChaCha20");

        private String algorithm;

        KeyGenAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    public static byte[] secret_key(KeyGenAlgorithm keyGenAlgo) {
        try {
            return KeyGenerator.getInstance(keyGenAlgo.algorithm).generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
