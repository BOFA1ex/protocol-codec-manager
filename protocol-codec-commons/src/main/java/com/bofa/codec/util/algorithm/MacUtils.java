package com.bofa.codec.util.algorithm;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author bofa1ex
 * @since 2020/3/20
 * @description 基于密钥的消息认证码算法工具类, 需要组合哈希算法
 * 比如hmacMD5 跟md5+salt原理一致
 * 相比较md5+salt, hmacMD5的优势
 *  1.HmacMD5使用的key长度是64字节，更安全；
 *  2.Hmac是标准算法，同样适用于SHA-1等其他哈希算法；
 *  3.Hmac输出和原有的哈希算法长度一致。
 */
public class MacUtils {

    public enum HmacAlgorithm{
        HmacMD5("HmacMD5"),
        HmacSHA256("HmacSHA256"),
        HmacSHA1("HmacSHA1");
        private String algorithm;

        HmacAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    public static byte[] hmac(HmacAlgorithm hmacAlgo,  byte[] data, byte[] hkey){
        try {
            Mac mac = Mac.getInstance(hmacAlgo.algorithm);
            SecretKey key = new SecretKeySpec(hkey, hmacAlgo.algorithm);
            mac.init(key);
            mac.update(data);
            return mac.doFinal();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
