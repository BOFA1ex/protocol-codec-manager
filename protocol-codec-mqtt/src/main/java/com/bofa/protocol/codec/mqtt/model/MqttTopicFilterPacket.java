package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalModel;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
import com.bofa.protocol.codec.method.convert.UnicodeConvertMethod;
import lombok.Data;
import org.springframework.context.annotation.Description;

/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("Topic Filter - 主题过滤器报文")
@CacheMapping("mqttTopicFilterPacket")
public class MqttTopicFilterPacket {

    /** 主题过滤器的长度(msg&lsb) */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer topicFilterLength;

    /** 主题过滤器 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttTopicFilterPacket", prop = "topicFilterLength", keyClazz = MqttTopicFilterPacket.class),
                    type= ByteBufInternalPoint.StepType.MODEL),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String topicFilter;

    /** 服务质量要求*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer requestedQos;
}
