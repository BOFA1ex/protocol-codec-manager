package com.bofa.codec.util.algorithm;

import org.junit.Test;

/**
 * @author bofa1ex
 * @since 2020/3/19
 */
public class TestCRCUtils {

    private byte[] testBytes = new byte[]{0x31, 0x32, 0x33, 0x34, 0x35};

    @Test
    public void testCRC_4_ITU() {
        final int crc = CRCUtils.crc4_itu(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_5_EPC(){
        final int crc = CRCUtils.crc5_epc(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_5_ITU(){
        final int crc = CRCUtils.crc5_itu(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_5_USB(){
        final int crc = CRCUtils.crc5_usb(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_6_ITU(){
        final int crc = CRCUtils.crc6_itu(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }


    @Test
    public void testCRC_7_MMC(){
        final int crc = CRCUtils.crc7_mmc(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_8(){
        final int crc = CRCUtils.crc8(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_8_ITU(){
        final int crc = CRCUtils.crc8_itu(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_8_MAXIM(){
        final int crc = CRCUtils.crc8_maxim(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_8_ROHC(){
        final int crc = CRCUtils.crc8_rohc(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_IBM(){
        final int crc = CRCUtils.crc16_ibm(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_MAXIM(){
        final int crc = CRCUtils.crc16_maxim(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_USB(){
        final int crc = CRCUtils.crc16_usb(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_MODBUS(){
        final int crc = CRCUtils.crc16_modbus(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_CCITT(){
        final int crc = CRCUtils.crc16_ccitt(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_CCITT_FALSE(){
        final int crc = CRCUtils.crc16_ccitt_false(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_X25(){
        final int crc = CRCUtils.crc16_x25(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_XMODEM(){
        final int crc = CRCUtils.crc16_xmodem(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_16_DNP(){
        final int crc = CRCUtils.crc16_dnp(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }

    @Test
    public void testCRC_32(){
        final int crc = CRCUtils.crc32(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }


    @Test
    public void testCRC_32_MPEG_2(){
        final int crc = CRCUtils.crc32_mpeg_2(testBytes, 0, 5);
        final String hex = Integer.toHexString(crc);
        System.out.println("hex -> " + hex);
    }
}
