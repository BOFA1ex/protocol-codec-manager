package com.bofa.protocol.codec.method;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author bofa1ex
 * @since 2020/2/17
 */
public interface ValidateMethod extends Serializable {

    /* 入口校验 */
    void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters);

    /* 出口组装 */
    void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters);

    default void afterMapperFlushBuffer(ByteBuf originalBuffer, ByteBuf mapperBuffer, Integer mapperIndex){
        final int markWriterIndex = originalBuffer.writerIndex();
        originalBuffer.markWriterIndex();
        originalBuffer.writerIndex(mapperIndex);
        originalBuffer.writeBytes(mapperBuffer);
        if (originalBuffer.writerIndex() < markWriterIndex){
            originalBuffer.resetWriterIndex();
        }
    }

}
