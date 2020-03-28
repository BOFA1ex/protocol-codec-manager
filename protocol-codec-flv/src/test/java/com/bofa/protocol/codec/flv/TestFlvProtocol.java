package com.bofa.protocol.codec.flv;

import com.bofa.protocol.codec.util.ParserHelper;
import com.bofa.protocol.codec.flv.model.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author bofa1ex
 * @since 2020/3/1w7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/spring-core-conf.xml")
public class TestFlvProtocol {

    final FlvParser flvParser = ParserHelper.getParser(FlvParser.class);

    @Autowired(required = false)
    FlvParser flvParser2;

    @Test
    public void encodeFlvFile() {
        final FlvFile flvFile = new FlvFile();
        EmbeddedChannel channel = new EmbeddedChannel();
        flvFile.setSignature("FLV");
        flvFile.setVersion(1);
        flvFile.setTypeFlagsVideo(1);
        flvFile.setTypeFlagsReserved(12);
        flvFile.setTypeFlagsAudio(1);
        flvFile.setTypeFlagsReserved2(1);
        flvFile.setDataOffset(15);
        final FlvTag flvTag = new FlvTag();
        flvTag.setPrevTagSize(1);
        flvTag.setDataLength(10);
        flvTag.setStreamId("1");
        flvTag.setTagType(18);
        flvTag.setTimeStamp(1000);
        flvTag.setTimeStampExtension(1000);
        final FlvScriptTagBody flvScriptTagBody = new FlvScriptTagBody();
        flvScriptTagBody.setData("00001");
        flvTag.setFlvScriptTagBody(flvScriptTagBody);
        final FlvTag flvTag2 = new FlvTag();
        flvTag2.setPrevTagSize(2);
        flvTag2.setDataLength(10);
        flvTag2.setStreamId("1");
        flvTag2.setTagType(18);
        flvTag2.setTimeStamp(2000);
        flvTag2.setTimeStampExtension(2000);
        final FlvScriptTagBody flvScriptTagBody2 = new FlvScriptTagBody();
        flvScriptTagBody2.setData("00002");
        flvTag2.setFlvScriptTagBody(flvScriptTagBody2);
        final FlvTag flvTag3 = new FlvTag();
        flvTag3.setPrevTagSize(3);
        flvTag3.setDataLength(10);
        flvTag3.setStreamId("1");
        flvTag3.setTagType(18);
        flvTag3.setTimeStamp(3000);
        flvTag3.setTimeStampExtension(3000);
        final FlvScriptTagBody flvScriptTagBody3 = new FlvScriptTagBody();
        flvScriptTagBody3.setData("00003");
        flvTag3.setFlvScriptTagBody(flvScriptTagBody3);
        flvFile.setFlvTags(Arrays.asList(flvTag, flvTag2, flvTag3));
        final ByteBuf encode = flvParser2.encode(flvFile, channel);
        final FlvFile decode = flvParser2.decode(encode, channel);
        System.out.println("decode -> " + decode);
    }

    @Test
    public void decodeFlvFile() throws URISyntaxException, IOException {
        final URL systemResource = ClassLoader.getSystemResource("./test.flv");
        final byte[] bytes = Files.readAllBytes(Paths.get(systemResource.toURI()));
        EmbeddedChannel channel = new EmbeddedChannel();
        FlvFile decode = flvParser2.decode(Unpooled.wrappedBuffer(bytes), channel);
        System.out.println("decode -> " + decode);
    }
}
