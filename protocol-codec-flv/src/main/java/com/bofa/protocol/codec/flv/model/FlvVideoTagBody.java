package com.bofa.protocol.codec.flv.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.BinaryIntegerConvertMethod;
import com.bofa.protocol.codec.method.convert.HexConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import lombok.Data;
import lombok.ToString;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
@ToString(exclude = "data")
@CacheMapping("flvVideoTagBody")
public class FlvVideoTagBody {
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "3"}
    )
    private Integer frameType;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"3", "7"}
    )
    private Integer codecId;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = HexConvertMethod.class
    )
    private String data;
}
