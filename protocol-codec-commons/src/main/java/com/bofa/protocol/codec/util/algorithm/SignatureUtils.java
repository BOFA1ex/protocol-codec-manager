package com.bofa.protocol.codec.util.algorithm;

import java.security.*;

/**
 * @author bofa1ex
 * @since 2020/3/20
 * @description 签名算法
 * 与RSA非对称加密算法不同, 签名为了确认发送方的身份有效性.
 * 是使用私钥进行加密, 并公开公钥, 因此对外可以用公钥解密加密信息, 确保信息不是伪造.
 */
public class SignatureUtils {

    public enum SignatureAlgorithm{
        NONEwithRSA("NONEwithRSA"),
        MD5withRSA("MD5withRSA"),
        SHA1withRSA("SHA1withRSA"),
        SHA224withRSA("SHA224withRSA"),
        SHA256withRSA("SHA256withRSA"),
        SHA512withRSA("SHA512withRSA"),
        NONEwithDSA	("NONEwithDSA"),
        SHA1withDSA	("SHA1withDSA"),
        SHA224withDSA	("SHA224withDSA"),
        SHA256withDSA	("SHA256withDSA"),
        SHA512withDSA	("SHA512withDSA"),
        NONEwithECDSA	("NONEwithECDSA"),
        SHA1withECDSA	("SHA1withECDSA"),
        SHA224withECDSA	("SHA224withECDSA"),
        SHA256withECDSA	("SHA256withECDSA"),
        SHA512withECDSA	("SHA512withECDSA");

        private String algorithm;

        SignatureAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }
    public static byte[] encrypt(SignatureAlgorithm signatureAlgo, byte[] data, PrivateKey privateKey){
        try{
            final Signature signature = Signature.getInstance(signatureAlgo.algorithm);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean decryptAndVerify(SignatureAlgorithm signatureAlgo, byte[] data, byte[] signed, PublicKey pubKey){
        try{
            final Signature signature = Signature.getInstance(signatureAlgo.algorithm);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(signed);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
