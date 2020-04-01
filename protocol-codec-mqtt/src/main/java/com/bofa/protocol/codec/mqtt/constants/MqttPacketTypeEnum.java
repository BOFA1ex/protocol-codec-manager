package com.bofa.protocol.codec.mqtt.constants;

/**
 * @author bofa1ex
 * @since 2020/4/1
 */
public enum  MqttPacketTypeEnum {
    Reversed(0),
    // C -> S
    CONNECT(1),
    CONNACK(2),
    // S -> C
    PUBLISH(3),
    PUBACK(4),
    PUBREC(5),
    PUBREL(6),
    PUBCOMP(7),
    // C -> S
    SUBSCRIBE(8),
    // S -> C
    SUBACK(9),
    // C -> S
    UNSUBSCRIBE(10),
    // S -> C
    UNSUBACK(11),
    // C -> S
    PINGREQ(12),
    // S -> C
    PINGRESP(13),
    // C -> S
    DISCONNECT(14),
    Reserved2(15);

    MqttPacketTypeEnum(int packetType) {
        this.packetType = packetType;
    }

    public int packetType;
}
