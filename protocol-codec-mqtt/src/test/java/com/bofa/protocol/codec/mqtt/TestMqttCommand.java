package com.bofa.protocol.codec.mqtt;

import com.bofa.protocol.codec.mqtt.outward.MqttConnectCommand;
import com.bofa.protocol.codec.util.ByteBufUtils;
import com.bofa.protocol.codec.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author bofa1ex
 * @since 2020/3/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(value = "/spring-core-conf.xml")
public class TestMqttCommand {

    @Autowired(required = false)
    private MqttParser mqttParser;

    private static final EmbeddedChannel channel = new EmbeddedChannel();

    private ByteBuf data;

    @Test
    public void testConnect() {
        final MqttConnectCommand command = new MqttConnectCommand();
        command.setPacketType(1);
        command.setPacketTypeSign(0);
        command.setCleanSession(1);
        command.setWillFlag(0);
        command.setWillQoS(0);
        command.setWillRetain(0);
        command.setUserNameFlag(1);
        command.setPasswordFlag(1);
        command.setKeepAliveTime(60);
        command.setClientIdLength(23);
        command.setClientId("mosq-PuSgaCcl50W4dL14GJ");
        command.setUseNameLength("bofa1ex".getBytes().length);
        command.setUserName("bofa1ex");
        command.setPasswordLength("123456".getBytes().length);
        command.setPassword("123456");
        data = mqttParser.encode(command, channel);
        final String hex = ByteBufUtils.buffer2Hex(data);
        System.out.println("hex -> " + hex);
//        final MqttConnectCommand decode = mqttParser.decode(encode.resetReaderIndex(), channel);
//        System.out.println("decode -> " + decode);
    }
}
