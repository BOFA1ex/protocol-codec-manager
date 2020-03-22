package com.bofa.codec.util.algorithm;

import javax.crypto.KeyAgreement;
import java.security.*;

/**
 * @author bofa1ex
 * @since 2020/3/20
 * @description 密钥交换算法 DH算法：Diffie-Hellman算法
 * 交换步骤
 * 1.甲方选择一个质数 p=509 底数g=5 随机数a 123 根据公式计算A=g^a mod p
 * 结果A=215, 甲方把p=509, g=5, A=215发给乙方
 * 2.乙方接收到数据后, 也选择一个随机数b 456 然后根据公式计算B=g^b mod p
 * 结果B=181, 乙方同时再计算s=A^b mod p, 结果是121
 * 3.乙方把B=181发给甲方, 甲方接收到后, 计算B^a mod p 的余数, 结果与乙算出的结果一致, 都为121
 * 协商后的密钥即为s=121
 *
 * 通过p1, g, A, B无法推算出s密钥，因为实际算法选择的质数很大.
 * 这里可以把甲方选择的随机数a理解为私钥, A为公钥, 乙方选择的b为私钥, B为公钥.
 * 算法本质是双方各自生成自己的私钥和公钥, 私钥仅自己可见, 交换公钥, 根据自己的私钥和对方的公钥,
 * 生成最终的密钥.
 *
 * 但是DH算法并未解决中间人攻击，即甲乙双方并不能确保与自己通信的是否真的是对方。消除中间人攻击需要其他方法
 */
public class DHUtils {

    public static byte[] generateSecretKey(byte[] receivedPubKeyBytes, KeyPair local){
        try {
            // 从byte[]恢复PublicKey:
            final PublicKey receivedPublicKey = KeyRecoverUtils.recoverPubKey(KeyRecoverUtils.KeyRecoverAlgorithm.DH, receivedPubKeyBytes);
            // 生成本地密钥:
            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(local.getPrivate()); // 本地的PrivateKey
            keyAgreement.doPhase(receivedPublicKey, true); // 对方的PublicKey
            // 生成SecretKey密钥:
            return keyAgreement.generateSecret();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
