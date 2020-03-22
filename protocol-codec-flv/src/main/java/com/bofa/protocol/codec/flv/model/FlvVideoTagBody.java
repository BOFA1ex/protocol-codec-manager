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
@ToString(exclude = "data")
@SpelMapping("flvVideoTagBody")
public class FlvVideoTagBody {
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"0", "3"})
    private Integer frameType;
    @ByteBufConvert(index = "0", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"3", "7"})
    private Integer codecId;
    @ByteBufConvert(index = "0", length = "#flvVideoTagBody_buffer.readableBytes()", convertMethod = HexConvertMethod.class)
    private String data;
}
