package com.bofa.codec.util;

import com.google.common.base.Strings;
import io.netty.buffer.*;


/**
 * @author bofa1ex
 * @since 2020/2/17
 */
public class ByteBufUtils {

    private static final PooledByteBufAllocator DEFAULT_ALLOCATOR = PooledByteBufAllocator.DEFAULT;

    /**
     * 缓冲区内容转换hex字符串
     */
    public static String buffer2Hex(ByteBuf buffer) {
        if (buffer == null) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        buffer.forEachByte(b -> {
            final String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                builder.append("0");
            }
            builder.append(hex);
            return true;
        });
        return builder.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转缓冲区, 需要指定缓冲区的容量(最左补0)
     */
    public static ByteBuf hex2Buffer(String hex, int capacity) {
        if (hex == null || hex.equals("")) {
            return null;
        }
        // 判断当前hex的长度是否小于缓冲区要求的capacity
        int len = hex.length();
        if (len < capacity << 1) {
            hex = Strings.padStart(hex, capacity << 1, '0');
        }
        return DEFAULT_ALLOCATOR.directBuffer(capacity).writeBytes(ByteBufUtil.decodeHexDump(hex));
    }

    /**
     * 缓冲区内容转换二进制字符串
     */
    public static String buffer2Binary(ByteBuf buffer) {
        StringBuilder sb = new StringBuilder();
        buffer.forEachByte(b -> {
            sb.append(ByteUtils.byte2Binary(b));
            return true;
        });
        return sb.toString();
    }

    /**
     * 二进制字符串转缓冲区
     */
    public static ByteBuf binary2Buffer(String binary) {
        // 二进制字符串分配的字节数默认不超过2 << 2
        final int i = Integer.parseInt(binary, 2);
        return DEFAULT_ALLOCATOR.directBuffer(2 << 2).writeBytes(new byte[]{(byte) i});
    }

    /**
     * 缓冲区内容转换ascii字符串
     */
    public static String buffer2Ascii(ByteBuf buffer) {
        final StringBuilder builder = new StringBuilder();
        buffer.forEachByte(b -> {
            builder.append((char) (b & 0xFF));
            return true;
        });
        return builder.toString().toUpperCase();
    }

    /**
     * ascii字符串转缓冲区, 需要指定缓冲区的容量
     */
    public static ByteBuf ascii2Buffer(String ascii, int capacity) {
        return DEFAULT_ALLOCATOR.directBuffer(capacity).writeBytes(ByteUtils.ascii2Bytes(ascii));
    }

}
