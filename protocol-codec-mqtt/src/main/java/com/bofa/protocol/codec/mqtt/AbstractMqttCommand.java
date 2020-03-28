package com.bofa.protocol.codec.mqtt;

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
                index = @ByteBufInternalPoint(step = "1"),
                length = @ByteBufInternalPoint(step = "4")
        ),
        mapper = @ByteBufValidation.Mapper(
                index = @ByteBufInternalPoint(step = "1"),
                length = @ByteBufInternalPoint(step = "4")
        ),
        validateMethod = MqttPacketLengthValidateMethod.class
)
public abstract class AbstractMqttCommand {

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "4"}
    )
    private Integer packetType;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"4", "7"}
    )
    private Integer packetTypeSign;

    /** 这里直接传默认最大长度4字节 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "4"),
            convertMethod = MqttPacketLengthConvertMethod.class
    )
    private Integer packetLength = 0xffffff7f;
}
