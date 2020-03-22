package com.bofa.protocol.codec.flv;


import com.bofa.commons.apt4j.annotate.protocol.*;
import com.bofa.protocol.codec.flv.exception.FlvDecodeResolveExceptionMethod;
import com.bofa.protocol.codec.flv.model.FlvFile;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author bofa1ex
 * @since 2020-01-02
 */
@Protocol(implName = "FlvParserImpl")
public interface FlvParser {

    @ByteBufDecode(resolveException = FlvDecodeResolveExceptionMethod.class)
    FlvFile decode(ByteBuf buffer, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 7)
    ByteBuf encode(FlvFile flvFile, Channel channel);
}

