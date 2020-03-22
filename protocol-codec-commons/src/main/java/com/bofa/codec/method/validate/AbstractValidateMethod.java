package com.bofa.codec.method.validate;

import com.bofa.codec.method.ValidateMethod;
import com.bofa.codec.util.ChannelSpelContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author bofa1ex
 * @since 2020/2/25
 */
public abstract class AbstractValidateMethod implements ValidateMethod {

    @Override
    public final void validate(ByteBuf buffer, Channel channel, String validateIndexExpr, String validateLengthExpr, String mapperIndexExpr, String mapperLengthExpr, String... parameters) {
        final Integer validateIndex = ChannelSpelContextUtils.processExprAndGet(validateIndexExpr, channel, Integer.class);
        final Integer validateLength = ChannelSpelContextUtils.processExprAndGet(validateLengthExpr, channel, Integer.class);
        final Integer mapperIndex = ChannelSpelContextUtils.processExprAndGet(mapperIndexExpr, channel, Integer.class);
        final Integer mapperLength = ChannelSpelContextUtils.processExprAndGet(mapperLengthExpr, channel, Integer.class);
        validate0(buffer, channel, validateIndex, validateLength, mapperIndex, mapperLength, parameters);
    }

    protected abstract void validate0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters);

    @Override
    public final void mapper(ByteBuf buffer, Channel channel, String validateIndexExpr, String validateLengthExpr, String mapperIndexExpr, String mapperLengthExpr, String... parameters) {
        final Integer validateIndex = ChannelSpelContextUtils.processExprAndGet(validateIndexExpr, channel, Integer.class);
        final Integer validateLength = ChannelSpelContextUtils.processExprAndGet(validateLengthExpr, channel, Integer.class);
        final Integer mapperIndex = ChannelSpelContextUtils.processExprAndGet(mapperIndexExpr, channel, Integer.class);
        final Integer mapperLength = ChannelSpelContextUtils.processExprAndGet(mapperLengthExpr, channel, Integer.class);
        int markWriterIndex = buffer.writerIndex();
        try {
            buffer.markWriterIndex();
            mapper0(buffer, channel, validateIndex, validateLength, mapperIndex, mapperLength, parameters);
        } finally {
            // 如果mapperIndex + mapperLength 小于当前写索引, 需要恢复上一次标记的写索引
            if (mapperIndex + mapperLength < markWriterIndex) {
                buffer.resetWriterIndex();
            }
        }
    }

    protected abstract void mapper0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters);
}
