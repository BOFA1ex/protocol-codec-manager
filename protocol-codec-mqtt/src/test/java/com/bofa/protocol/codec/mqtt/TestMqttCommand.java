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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * @author bofa1ex
 * @since 2020/3/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/spring-core-conf.xml")
public class TestMqttCommand {

    @Autowired
    private MqttParser mqttParser;

    private static final  EmbeddedChannel channel = new EmbeddedChannel();

    private ByteBuf data;

    @Test
    public void testConnect(){
        final MqttConnectCommand command = new MqttConnectCommand();
        command.setPacketType(1);
        command.setPacketTypeSign(0);
        command.setCleanSession(1);
        command.setWillFlag(1);
        command.setWillQoS(1);
        command.setUserNameFlag(1);
        command.setPasswordFlag(1);
        command.setKeepAliveTime(10);
        command.setClientIdLength(4);
        command.setClientId("0001");
        command.setWillTopicLength("test/1".getBytes().length);
        command.setWillTopic("test/1");
        command.setUseNameLength("bofa1ex".getBytes().length);
        command.setUserName("bofa1ex");
        command.setPasswordLength("123456".getBytes().length);
        command.setPassword("123456");
        data = mqttParser.encode(command, channel);
//        final String hex = ByteBufUtils.buffer2Hex(encode);
//        System.out.println("hex -> " + hex);
//        final MqttConnectCommand decode = mqttParser.decode(encode.resetReaderIndex(), channel);
//        System.out.println("decode -> " + decode);
    }

    @After
    public void flush() throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(1883));
        socketChannel.write(data.nioBuffer());
    }


}
