package com.bofa.codec.util.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.util.Objects;

/**
 * @author bofa1ex
 * @since 2020/3/20
 * @description 摘要算法工具类
 *
 *          摘要算法          输出长度（位）           输出长度（字节）
 *      *
 *      *  MD5              128 bits               16 bytes
 *      *  SHA-1	        160 bits	           20 bytes
 *      *  RipeMD-160       160 bits               20 bytes
 *      *  SHA-256	        256 bits	           32 bytes
 *      *  SHA-512          512 bits	           64 bytes
 */
public class DigestUtils {

    public enum DigestAlgorithm{
        MD5("MD5"),
        SHA1("SHA-1"),
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        RipeMD160("RipeMD160");
        private String algorithm;

        DigestAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    public static byte[] digest(DigestAlgorithm digestAlgo, byte[] data, byte... salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(digestAlgo.algorithm);
            md.update(data);
            md.update(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(md).digest(); // 16 bytes
    }

    static {
        // 注册BouncyCastle:
        Security.addProvider(new BouncyCastleProvider());
    }
}
