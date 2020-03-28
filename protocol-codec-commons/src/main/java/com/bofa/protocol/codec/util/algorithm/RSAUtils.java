package com.bofa.protocol.codec.util.algorithm;

import javax.crypto.Cipher;
import java.security.*;

/**
 * @author bofa1ex
 * @since 2020/3/20
 * @description 非对称加密的典型算法就是RSA算法
 * 非对称和对称加解密结合实际应用步骤
 * 1. 甲方生成一个随机的AES口令，然后用乙方的公钥通过RSA加密这个口令，并发给乙方
 * 2. 乙方用自己的私钥解密得到AES口令
 * 3. 双方用这个AES口令进行后续的通信
 *
 * 只使用非对称加密算法不能防止中间人攻击
 */
public class RSAUtils {

    public static byte[] encrypt(byte[] data, PublicKey pubKey){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
