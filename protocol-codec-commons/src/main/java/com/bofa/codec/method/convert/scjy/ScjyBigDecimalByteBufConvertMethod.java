package com.bofa.codec.method.convert.scjy;

import com.bofa.codec.method.ConvertMethod;
import com.bofa.codec.method.convert.HexConvertMethod;
import com.bofa.codec.util.ByteBufUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author bofa1ex
 * @since 2020/3/16
 */
@Component
public class ScjyBigDecimalByteBufConvertMethod implements ConvertMethod<BigDecimal> {

    public static final ScjyBigDecimalByteBufConvertMethod INSTANCE = new ScjyBigDecimalByteBufConvertMethod();

    @Override
    public BigDecimal decode(ByteBuf buffer, Channel channel, String... parameters) {
        if (parameters == null || parameters.length == 0) {
            throw new RuntimeException("parameters需指定小数位prec");
        }
        int prec = Integer.parseInt(parameters[0]);
        String hex = HexConvertMethod.INSTANCE.decode(buffer, channel, parameters);
        String integerPartsHex = hex.substring(0, hex.length() - prec);
        String precPartsHex = hex.substring(hex.length() - prec);
        BigDecimal result = new BigDecimal(integerPartsHex).add(new BigDecimal(new BigInteger(precPartsHex), prec));
        if (parameters.length > 1) {
            boolean requireValidSign = Boolean.parseBoolean(parameters[1]);
            if (requireValidSign && !hex.startsWith("0")) {
                // 如果符号位不为0, 表示当前值为负数
                result = result.negate();
            }
        }
        return result;
    }

    @Override
    public ByteBuf encode(BigDecimal value, int capacity, Channel channel, String... parameters) {
        if (value != null && parameters != null && parameters.length > 0) {
            ByteBuf replaceBuffer;
            // 符号位处理
            if (parameters.length > 1 && Boolean.parseBoolean(parameters[1])) {
                if (value.signum() < 0) {
                    // 负数处理
                    String[] parts = value.toString().split("\\.");
                    String intHex = Strings.padStart(parts[0], capacity - 2 - 2, '0');
                    String fracHex = Strings.padEnd(parts[1], 2, '0');
                    replaceBuffer = ByteBufUtils.hex2Buffer("80" + intHex + fracHex, capacity);
                } else {
                    // 正数处理
                    String[] parts = value.toString().split("\\.");
                    String intHex = Strings.padStart(parts[0], capacity - 2 - 2, '0');
                    String fracHex = Strings.padEnd(parts[1], 2, '0');
                    replaceBuffer = ByteBufUtils.hex2Buffer("00" + intHex + fracHex, capacity);
                }
            } else {
                if (value.signum() < 0) {
                    // 负数处理
                    String[] parts = value.toString().split("\\.");
                    String intHex = Strings.padStart(parts[0], capacity - 2, '0');
                    String fracHex = Strings.padEnd(parts[1], 2, '0');
                    replaceBuffer = ByteBufUtils.hex2Buffer(intHex + fracHex, capacity);

                } else {
                    // 正数处理
                    String[] parts = value.toString().split("\\.");
                    String intHex = Strings.padStart(parts[0], capacity - 2, '0');
                    String fracHex = Strings.padEnd(parts[1], 2, '0');
                    replaceBuffer = ByteBufUtils.hex2Buffer(intHex + fracHex, capacity);
                }
            }
            return replaceBuffer;
        }
        return null;
    }
}
