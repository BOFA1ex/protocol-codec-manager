package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;

/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("PUBLISH - 发布消息报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttPublishPacket")
public class MqttPublishPacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 ********************************/

    /** 主题名长度 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer topicNameLength;

    /** 主题名 用于识别有效载荷数据应该发布到哪个信息通道 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttPublishPacket", prop = "topicNameLength", keyClazz = MqttPublishPacket.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String topicName;

    /** 报文标识符, 只有qos等级为1或2时, 该字段才出现在publish报文*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttPublishPacket", prop = "qosLevel", keyClazz = MqttPublishPacket.class),
                    operator = "!=", compareValue = "0"
            ),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer packetIdentifier;


    /* ******************************** 有效载荷 ********************************/

    /** 有效载荷 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String payload;
}
