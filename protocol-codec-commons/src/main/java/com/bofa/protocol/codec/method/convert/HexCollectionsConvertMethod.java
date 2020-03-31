package com.bofa.protocol.codec.method.convert;

import com.bofa.protocol.codec.method.ConvertMethod;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Component
@Description("缓冲区字节流转换为Hex的集合")
public class HexCollectionsConvertMethod implements ConvertMethod<List<String>> {

    @Autowired
    private HexConvertMethod hexConvertMethod;

    @Override
    public List<String> decode(ByteBuf buffer, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 1){
            throw new IllegalArgumentException("#HexCollectionsConvertMethod需要指定内部hex的固定长度");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("#HexCollectionsConvertMethod需要指定内部hex的固定长度不能为空");
        }
        final int internalHexLength = Integer.parseInt(parameters[0]);
        //todo 这里以后再考虑, 需不需要指定list的实现类型
        final List<String> hexs = Lists.newArrayList();
        while (buffer.isReadable()){
            hexs.add(hexConvertMethod.decode(buffer.readSlice(internalHexLength), channel, parameters));
        }
        return hexs;
    }

    @Override
    public ByteBuf encode(List<String> hexs, int capacity, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 1){
            throw new IllegalArgumentException("#HexCollectionsConvertMethod需要指定内部hex的固定长度");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("#HexCollectionsConvertMethod需要指定内部hex的固定长度不能为空");
        }
        final int internalHexLength = Integer.parseInt(parameters[0]);
        //todo 这里以后再考虑, 需不需要指定buffer的初始容量
        final ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(2 << 7);
        for (final String hex : hexs) {
            final ByteBuf encode = hexConvertMethod.encode(hex, internalHexLength, channel, parameters);
            buffer.writeBytes(encode);
        }
        return buffer;
    }
}
