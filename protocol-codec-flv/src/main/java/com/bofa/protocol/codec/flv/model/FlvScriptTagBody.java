package com.bofa.protocol.codec.flv.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.HexConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import lombok.Data;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
//@ToString(exclude = "data")
@CacheMapping("flvScriptTagBody")
public class FlvScriptTagBody {
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = HexConvertMethod.class
    )
    private String data;
}
