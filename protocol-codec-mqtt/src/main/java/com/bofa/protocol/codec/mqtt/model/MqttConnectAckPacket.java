package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Data
@Description("CONNACK - 确认连接请求报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttConnectAckPacket")
public class MqttConnectAckPacket extends AbstractMqttPacket {

    public static final int ACK_SC = 0;
    /** 协议版本不支持 */
    public static final int ACK_PROTOCOL_VERSION_ERROR = 1;
    /** 不合格的客户端标识符 */
    public static final int ACK_CLIENT_IDENTIFIER_ERROR = 2;
    /** 服务端不可用 */
    public static final int ACK_INTERNAL_ERROR = 3;
    /** 无效的用户名/密码 */
    public static final int ACK_INVALID_USERNAME_OR_PWD = 4;
    /** 未授权 */
    public static final int ACK_AUTHC_ERROR = 5;

    public static MqttConnectAckPacket mapper(MqttConnectPacket mqttConnectPacket, int ack, boolean sessionExists) {
        final MqttConnectAckPacket mqttConnectAckPacket = new MqttConnectAckPacket();
        mqttConnectAckPacket.setPacketType(MqttPacketTypeEnum.CONNACK.packetType);
        mqttConnectAckPacket.setDupSign(0);
        mqttConnectAckPacket.setQosLevel(0);
        mqttConnectAckPacket.setPacketLength(2);
        // 如果收到清理会话标志为0的报文，并且服务端已经保存了该会话状态
        if (mqttConnectPacket.getCleanSession() == 0 && sessionExists) {
            mqttConnectAckPacket.setSessionPresent(1);
        }
        mqttConnectAckPacket.setAckSign(ack);
        mqttConnectAckPacket.setSessionPresent(0);
        return mqttConnectAckPacket;
    }

    /* ******************************** 可变报文头部 固定2字节 ********************************/
    /** 当前会话标志 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"7", "8"}
    )
    private Integer sessionPresent;

    /** 协议名不正确的情况下, 不处理connect报文, 防火墙可以根据该字段来识别mqtt流量 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer ackSign;
}
