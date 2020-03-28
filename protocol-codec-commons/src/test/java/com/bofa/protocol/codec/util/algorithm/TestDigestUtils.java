package com.bofa.protocol.codec.util.algorithm;

import com.bofa.protocol.codec.util.ByteUtils;
import org.junit.Test;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestDigestUtils {

    @Test
    public void md5() {
        final byte[] md5bytes = DigestUtils.digest(DigestUtils.DigestAlgorithm.MD5,
                "hello world".getBytes());
        final String hex = ByteUtils.bytes2Hex(md5bytes);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void sha256() {
        final byte[] sha256Bytes = DigestUtils.digest(DigestUtils.DigestAlgorithm.SHA256,
                "hello world".getBytes());
        final String hex = ByteUtils.bytes2Hex(sha256Bytes);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void ripeMD160() {
        final byte[] ripeMD160Bytes = DigestUtils.digest(DigestUtils.DigestAlgorithm.RipeMD160,
                "hello world".getBytes());
        final String hex = ByteUtils.bytes2Hex(ripeMD160Bytes);
        System.out.println("hex -> " + hex);
    }



}
