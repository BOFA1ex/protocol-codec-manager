package com.bofa.protocol.codec.mqtt;

import com.bofa.protocol.codec.mqtt.model.MqttConnectPacket;
import com.bofa.protocol.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

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
        final MqttConnectPacket command = new MqttConnectPacket();
        command.setPacketType(1);
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
        final String hex = ByteBufUtils.buffer2HexNonRead(data);
        System.out.println("hex -> " + hex);
//        final MqttConnectCommand decode = mqttParser.decode(encode.resetReaderIndex(), channel);
//        System.out.println("decode -> " + decode);
    }
//
//    @After
//    public void flush() throws IOException, InterruptedException {
//        final SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.connect(new InetSocketAddress(24028));
//        final ByteBuf leftBuffer = data.slice(0, data.writerIndex() - 1);
//        final ByteBuf rightBuffer = data.slice(data.writerIndex() - 1, 1);
//        socketChannel.write(leftBuffer.nioBuffer());
//        TimeUnit.SECONDS.sleep(10);
//        socketChannel.write(rightBuffer.nioBuffer());
//    }
}
