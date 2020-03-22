package com.bofa.codec.util;


import com.google.common.base.Strings;

import java.util.Arrays;

/**
 * @author bofa1ex
 * @since 2020-01-02
 * {@link io.netty.buffer.ByteBufUtil}
 * {@link com.bofa.codec.util.ByteBufUtils}
 */
public class ByteUtils {

    private static final String HEX_CHAR = "0123456789ABCDEF";

    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        final StringBuilder stringBuffer = new StringBuilder();
        for (final byte b : bytes) {
            final String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hex);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static byte[] hex2Bytes(String hex) {
        if (hex == null) {
            return null;
        }
        hex = hex.toUpperCase();
        int length = hex.length() / 2;
        byte[] dst = new byte[length];
        final char[] hexChars = hex.toCharArray();
        for (int i = 0, pos = i * 2; i < hexChars.length - 1; i++) {
            dst[i] = (byte) (char2Byte(hexChars[pos]) << 4 | char2Byte(hexChars[pos + 1]));
        }
        return dst;
    }

    public static byte char2Byte(char c) {
        return (byte) HEX_CHAR.indexOf(c);
    }

    public static String byte2Hex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }

    public static byte hex2Byte(String hex) {
        return hex2Bytes(hex)[0];
    }

    public static String byte2Binary(byte b) {
        // 这里需要无符号位byte->int, 否则直接toBinaryString处理，会当有符号位-127~127处理
        final String binary = Integer.toBinaryString(Byte.toUnsignedInt(b));
        return Strings.padStart(binary, 8, '0');
    }

    public static String bytes2Binary(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byte2Binary(b));
        }
        return sb.toString();
    }

    public static byte binary2Byte(String binary) {
        final int i = Integer.parseInt(binary, 2);
        return (byte) i;
    }

    public static String bytes2Ascii(byte[] bytes) {
        final char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = ((char) (bytes[i] & 0xFF));
        }
        return new String(chars);
    }

    public static byte[] ascii2Bytes(String ascii) {
        final char[] chars = ascii.toCharArray();
        final byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = ((byte) (chars[i] & 0xFF));
        }
        return bytes;
    }

    public static int bytes2Int(byte[] bytes) {
        return Integer.parseInt(bytes2Hex(bytes), 16);
    }

    public static byte[] int2Bytes(int val) {
        return hex2Bytes(Integer.toHexString(val));
    }

    public static byte[] copyOfRange(byte[] bytes, int start, int end) {
        if (start < 0) {
            start = start + bytes.length;
        }
        if (end <= 0) {
            end = end + bytes.length;
        }
        return Arrays.copyOfRange(bytes, start, end);
    }

    public static void replaceBytes(byte[] originalBytes, int start, int end, byte[] replaceBytes) {
        if (start < 0) {
            start = start + originalBytes.length;
        }
        if (end <= 0) {
            end = end + originalBytes.length;
        }
        if (replaceBytes.length != (end - start)) {
            throw new IllegalArgumentException("字节数组长度异常");
        }
        if (replaceBytes.length + start - originalBytes.length > 0) {
            throw new IllegalArgumentException("字节数组长度异常");
        }
        System.arraycopy(replaceBytes, 0, originalBytes, start, replaceBytes.length);
    }
}
