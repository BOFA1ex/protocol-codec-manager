package com.bofa.codec.method;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author bofa1ex
 * @since 2020/2/17
 */
public interface ValidateMethod extends Serializable {

    /* 入口校验 */
    void validate(ByteBuf buffer, Channel channel, String validateIndexExpr, String validateLengthExpr, String mapperIndexExpr, String mapperLengthExpr, String... parameters);

    /* 出口组装 */
    void mapper(ByteBuf buffer, Channel channel, String validateIndexExpr, String validateLengthExpr, String mapperIndexExpr, String mapperLengthExpr, String... parameters);
}
