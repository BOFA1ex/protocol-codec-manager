package com.bofa.protocol.codec.util.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Security;

/**
 * @author bofa1ex
 * @description 口令加解密工具类
 * @since 2020/3/20
 */
public class PBEUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 口令加密
     *
     * @param password 口令明文
     * @param salt     盐
     * @param input    原文内容
     * @param loop     循环次数
     */
    public static byte[] encrypt(String password, byte[] salt, byte[] input, int loop) {
        try {
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory skeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            SecretKey skey = skeyFactory.generateSecret(keySpec);
            PBEParameterSpec pbeps = new PBEParameterSpec(salt, loop);
            Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            cipher.init(Cipher.ENCRYPT_MODE, skey, pbeps);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 口令解密
     *
     * @param password 口令明文
     * @param salt     盐
     * @param input    原文内容
     * @param loop     循环次数
     */
    public static byte[] decrypt(String password, byte[] salt, byte[] input, int loop) {
        try {
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory skeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            SecretKey skey = skeyFactory.generateSecret(keySpec);
            PBEParameterSpec pbeps = new PBEParameterSpec(salt, loop);
            Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            cipher.init(Cipher.DECRYPT_MODE, skey, pbeps);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
