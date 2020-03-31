package com.bofa.protocol.codec.method.convert;


import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;


/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
@Description("缓冲区字节流转换为Unicode, 需要给定字符集Charset")
public class UnicodeConvertMethod implements ConvertMethod<String> {

    public static final UnicodeConvertMethod INSTANCE = new UnicodeConvertMethod();

    public static final String CHARSET_NAME_UTF_8 = "UTF-8";
    public static final String CHARSET_NAME_US_ASCII = "US-ASCII";
    public static final String CHARSET_NAME_ISO_8859_1 = "ISO-8859-1";
    public static final String CHARSET_NAME_UTF_16BE = "UTF-16BE";
    public static final String CHARSET_NAME_UTF_16LE = "UTF-16LE";
    public static final String CHARSET_NAME_UTF_16 = "UTF-16";

    @Override
    public String decode(ByteBuf buffer, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 1){
            throw new IllegalArgumentException("unicode需要指定charsetName");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("unicode parameter[1] charsetName不能为空");
        }
        return buffer != null ? ByteBufUtils.buffer2Unicode(buffer, parameters[0]) : "";
    }

    @Override
    public ByteBuf encode(String s, int capacity, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 1){
            throw new IllegalArgumentException("unicode#encode需要指定charsetName");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("unicode#encode需要指定charsetName");
        }
        return ByteBufUtils.unicode2Buffer(s, capacity, parameters[0]);
    }

}
