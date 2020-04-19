package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.protocol.*;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.BinaryIntegerConvertMethod;
import com.bofa.protocol.codec.mqtt.convert.MqttPacketLengthConvertMethod;
import com.bofa.protocol.codec.mqtt.validate.MqttPacketLengthValidateMethod;
import lombok.Data;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Data
@ByteBufValidation(
        validate = @ByteBufValidation.Validate(
                index = @ByteBufInternalPoint(),
                length = @ByteBufInternalPoint()
        ),
        mapper = @ByteBufValidation.Mapper(
                index = @ByteBufInternalPoint(step = "1"),
                length = @ByteBufInternalPoint(step = "4")
        ),
        validateMethod = MqttPacketLengthValidateMethod.class
)
public abstract class AbstractMqttPacket {

    /** 控制报文类型 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "4"}
    )
    private Integer packetType;

    /** 表示c/s第一次请求发送publish报文 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"4", "5"}
    )
    private Integer dupSign;

    /**
     * 服务质量等级
     * 0 最多发一次
     * 1 至少发一次
     * 2 只发一次
     */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"5", "7"}
    )
    private Integer qosLevel;

    /**
     * 保留标志
     * 服务端发送Publish报文给客户端时, 如果该消息时作为客户端一个新的订阅结果发送, 必须设置为1
     * 反之为0
     */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"7", "8"}
    )
    private Integer reverse;

    /** 这里直接传默认最大长度4字节 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = MqttPacketLengthConvertMethod.class
    )
    private Integer packetLength = 0xffffff7f;
}
