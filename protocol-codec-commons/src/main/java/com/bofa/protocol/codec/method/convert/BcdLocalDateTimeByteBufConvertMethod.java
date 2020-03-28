package com.bofa.protocol.codec.method.convert;

import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bofa1ex
 * @since 2020/3/16
 */
@Component
public class BcdLocalDateTimeByteBufConvertMethod implements ConvertMethod<LocalDateTime> {

    public static final BcdLocalDateTimeByteBufConvertMethod INSTANCE = new BcdLocalDateTimeByteBufConvertMethod();

    private static final String DEFAULT_DATE_TIME_FORMATTER = "yyyyMMddHHmmss";

    @Override
    public LocalDateTime decode(ByteBuf buffer, Channel channel, String... parameters) {
        if (parameters == null || parameters.length == 0){
            parameters = new String[]{DEFAULT_DATE_TIME_FORMATTER};
        }
        final String dateTime = ByteBufUtils.buffer2Hex(buffer);
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(parameters[0]));
    }

    @Override
    public ByteBuf encode(LocalDateTime localDateTime, int capacity, Channel channel, String... parameters) {
        if (parameters == null || parameters.length == 0){
            parameters = new String[]{DEFAULT_DATE_TIME_FORMATTER};
        }
        final String bcdDateStr = localDateTime.format(DateTimeFormatter.ofPattern(parameters[0]));
        return ByteBufUtils.hex2Buffer(bcdDateStr, capacity);
    }
}
