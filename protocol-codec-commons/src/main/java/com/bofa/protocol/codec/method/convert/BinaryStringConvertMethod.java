package com.bofa.protocol.codec.method.convert;


import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class BinaryStringConvertMethod implements ConvertMethod<String> {

    @Override
    public String decode(ByteBuf buffer, Channel channel, String... parameters) {
        if (buffer == null) {
            return "";
        }
        // 如果没有传parameters, 根据传入的缓冲区可读大小来确定二进制区间
        if (parameters == null || parameters.length == 0) {
            parameters = new String[]{"0", buffer.readableBytes() * 8 + ""};
        }
        if (parameters.length != 2) {
            throw new IllegalArgumentException("parameters长度不合规范");
        }
        return ByteBufUtils.buffer2Binary(buffer).substring(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
    }

    @Override
    public ByteBuf encode(String original, int capacity, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 2) {
            throw new IllegalArgumentException("parameters不可为空或长度不合规范");
        }
        final int lOffset = Integer.parseInt(parameters[0]);
        final int rOffset = Integer.parseInt(parameters[1]);
        final int adjustment = rOffset - lOffset;
        if (original.length() != adjustment){
            throw new IllegalArgumentException("original长度不符合要求 original.length() != " + adjustment);
        }
        final int total = capacity * 8;
        final String leftBinary = Strings.padStart("", lOffset, '0');
        final String totalBinary = Strings.padEnd(leftBinary + original, total, '0');
        return ByteBufUtils.binary2Buffer(totalBinary);
    }
}
