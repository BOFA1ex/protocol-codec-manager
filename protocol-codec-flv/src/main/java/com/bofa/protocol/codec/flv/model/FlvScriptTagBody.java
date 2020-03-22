package com.bofa.protocol.codec.flv.model;

import com.bofa.codec.method.convert.HexConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.spel.SpelMapping;
import lombok.Data;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
//@ToString(exclude = "data")
@SpelMapping("flvScriptTagBody")
public class FlvScriptTagBody {
    @ByteBufConvert(index = "0", length = "#flvScriptTagBody_buffer.readableBytes()", convertMethod = HexConvertMethod.class)
    private String data;
}
