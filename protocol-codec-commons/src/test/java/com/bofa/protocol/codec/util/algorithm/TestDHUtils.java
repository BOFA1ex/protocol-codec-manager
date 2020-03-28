package com.bofa.protocol.codec.util.algorithm;


import com.bofa.protocol.codec.util.ByteUtils;
import org.junit.Test;

import java.security.KeyPair;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
public class TestDHUtils {

    @Test
    public void test() {
        final KeyPair local = KeyPairGenUtils.generateKeyPair(KeyPairGenUtils.KeyPairAlgorithm.DH);
        final KeyPair remote = KeyPairGenUtils.generateKeyPair(KeyPairGenUtils.KeyPairAlgorithm.DH);
        final byte[] localComputedSecretKey = DHUtils.generateSecretKey(remote.getPublic().getEncoded(), local);
        final byte[] remoteComputedSecretKey = DHUtils.generateSecretKey(local.getPublic().getEncoded(), remote);
        final String localHex = ByteUtils.bytes2Hex(localComputedSecretKey);
        final String remoteHex = ByteUtils.bytes2Hex(remoteComputedSecretKey);
        System.err.println("localHex -> " + localHex);
        System.err.println("remoteHex -> " + remoteHex);
    }
}
