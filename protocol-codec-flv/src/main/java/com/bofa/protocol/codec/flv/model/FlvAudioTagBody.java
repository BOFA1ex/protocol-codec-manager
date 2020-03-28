package com.bofa.protocol.codec.flv.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import lombok.Data;
import lombok.ToString;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
@ToString(exclude = "soundData")
@CacheMapping("flvAudioTagBody")
public class FlvAudioTagBody {
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "3"}
    )
    private Integer soundFormat;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"3", "5"}
    )
    private Integer soundRate;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"5", "6"}
    )
    private Integer soundSize;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"6", "7"}
    )
    private Integer soundType;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = HexConvertMethod.class
    )
    private String soundData;
}
