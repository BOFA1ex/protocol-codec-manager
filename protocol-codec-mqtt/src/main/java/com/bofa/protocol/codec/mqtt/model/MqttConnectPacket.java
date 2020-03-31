package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import lombok.*;
import org.springframework.context.annotation.Description;

import javax.validation.constraints.Size;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Data
@Description("CONNECT - 连接请求报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttConnectPacket")
public class MqttConnectPacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 固定10字节 ********************************/

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer protocolNameLength = 4;

    /** 协议名不正确的情况下, 不处理connect报文, 防火墙可以根据该字段来识别mqtt流量 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "4"),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    @Size
    private String protocolName = "MQTT";

    /** 如果不支持该协议级别, 在connAck报文中响应返回码0x01, 并断开客户端连接 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer protocolLevel = 4;

    /** 如果connect控制报文的保留标志位不为0, 直接断开客户端连接 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"7", "8"}
    )
    private Integer reserved = 0;

    /** 清理会话, 指定了会话状态的处理方式, 这个标志位控制会话状态的生存时间 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"6", "7"}
    )
    private Integer cleanSession;

    /**
     * 遗嘱标志, 如果设置为1， 表示如果连接请求被接受了，willQoS消息必须存储在服务端并且与这个网络连接关联.
     * 并且在网络连接关闭时, 服务端必须发布这个遗嘱信息, 除非服务端收到disconnect报文并删除了这个遗嘱信息
     */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"5", "6"}
    )
    private Integer willFlag;

    /** 遗嘱QoS */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"3", "5"}
    )
    private Integer willQoS;

    /** 遗嘱保留 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"2", "3"}
    )
    private Integer willRetain;

    /** 密码标志 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"1", "2"}
    )
    private Integer passwordFlag;

    /** 用户名标志 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "1"}
    )
    private Integer userNameFlag;

    /** 保持连接时间 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer keepAliveTime;

    /* ******************************** PayLoad ********************************/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer clientIdLength;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "clientIdLength", keyClazz = MqttConnectPacket.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String clientId;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class,
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "willFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            )
    )
    private Integer willTopicLength;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "willTopicLength", keyClazz = MqttConnectPacket.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "willFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            ),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8

    )
    private String willTopic;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class,
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "userNameFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            )
    )
    private Integer useNameLength;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "useNameLength", keyClazz = MqttConnectPacket.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "userNameFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            ),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String userName;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class,
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "passwordFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            )
    )
    private Integer passwordLength;

    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "passwordLength", keyClazz = MqttConnectPacket.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "mqttConnectPacket", prop = "passwordFlag", keyClazz = MqttConnectPacket.class),
                    compareValue = "1"
            ),
            convertMethod = UnicodeConvertMethod.class,
            parameters = UnicodeConvertMethod.CHARSET_NAME_UTF_8
    )
    private String password;
}
