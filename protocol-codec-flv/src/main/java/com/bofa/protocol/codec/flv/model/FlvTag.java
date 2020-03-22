package com.bofa.protocol.codec.flv.model;

import com.bofa.codec.method.convert.HexConvertMethod;
import com.bofa.codec.method.convert.IntegerConvertMethod;
import com.bofa.codec.method.convert.flv.FlvTimeStampExtensionConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.spel.SpelMapping;
import lombok.Data;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
@SpelMapping("flvTag")
public class FlvTag {
    @ByteBufConvert(index = "0", length = "4", convertMethod = IntegerConvertMethod.class)
    private Integer prevTagSize;
    @ByteBufConvert(index = "4", length = "1", convertMethod = IntegerConvertMethod.class)
    private Integer tagType;
    @ByteBufConvert(index = "5", length = "3", convertMethod = IntegerConvertMethod.class)
    private Integer dataLength;
    @ByteBufConvert(index = "8", length = "3", convertMethod = IntegerConvertMethod.class)
    private Integer timeStamp;
    @ByteBufConvert(index = "8", length = "4", convertMethod = FlvTimeStampExtensionConvertMethod.class)
    private Integer timeStampExtension;
    @ByteBufConvert(index = "12", length = "3", convertMethod = HexConvertMethod.class)
    private String streamId;
    @ByteBufConvert(index = "15", length = "#flvTag.dataLength", condition = "#flvTag.tagType == 8")
    private FlvAudioTagBody flvAudioTagBody;
    @ByteBufConvert(index = "15", length = "#flvTag.dataLength", condition = "#flvTag.tagType == 9")
    private FlvVideoTagBody flvVideoTagBody;
    @ByteBufConvert(index = "15", length = "#flvTag.dataLength", condition = "#flvTag.tagType == 18")
    private FlvScriptTagBody flvScriptTagBody;
}
