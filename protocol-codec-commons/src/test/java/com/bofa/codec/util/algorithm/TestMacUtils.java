package com.bofa.codec.util.algorithm;

import com.bofa.codec.util.ByteUtils;
import org.junit.Test;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestMacUtils {

    @Test
    public void hmac() {
        final byte[] hmacBytes = MacUtils.hmac(MacUtils.HmacAlgorithm.HmacMD5,
                new byte[]{0x01, 0x02},
                KeyGenUtils.secret_key(KeyGenUtils.KeyGenAlgorithm.HmacMD5)
        );
        final String hex = ByteUtils.bytes2Hex(hmacBytes);
        System.out.println("hex -> " + hex);
    }
}
