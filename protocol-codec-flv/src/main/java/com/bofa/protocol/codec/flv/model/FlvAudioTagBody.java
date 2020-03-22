package com.bofa.protocol.codec.flv.model;

import com.bofa.codec.method.convert.BinaryIntegerConvertMethod;
import com.bofa.codec.method.convert.HexConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.spel.SpelMapping;
import lombok.Data;
import lombok.ToString;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
@ToString(exclude = "soundData")
@SpelMapping("flvAudioTagBody")
public class FlvAudioTagBody {
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"0", "3"})
    private Integer soundFormat;
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"3", "5"})
    private Integer soundRate;
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"5", "6"})
    private Integer soundSize;
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"6", "7"})
    private Integer soundType;
    @ByteBufConvert(index = "1", length = "#flvAudioTagBody_buffer.readableBytes()", convertMethod = HexConvertMethod.class)
    private String soundData;
}
