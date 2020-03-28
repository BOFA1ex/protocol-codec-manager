package com.bofa.protocol.codec.flv.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.HexConvertMethod;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.protocol.codec.flv.convert.FlvTimeStampExtensionConvertMethod;
import lombok.Data;

/**
 * @author bofa1ex
 * @since 2020-01-04
 */
@Data
@CacheMapping("flvTag")
public class FlvTag {
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "4"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer prevTagSize;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer tagType;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "3"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer dataLength;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "3"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer timeStamp;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-3"),
            length = @ByteBufInternalPoint(step = "4"),
            convertMethod = FlvTimeStampExtensionConvertMethod.class
    )
    private Integer timeStampExtension;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "3"),
            convertMethod = HexConvertMethod.class
    )
    private String streamId;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class), compareValue = "8"
            )
    )
    private FlvAudioTagBody flvAudioTagBody;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class), compareValue = "9"
            )
    )
    private FlvVideoTagBody flvVideoTagBody;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class),
                    type = ByteBufInternalPoint.StepType.MODEL
            ),
            condition = @ByteBufInternalCondition(model = @ByteBufInternalModel(
                    key = "flvTag", prop = "tagType", keyClazz = FlvTag.class), compareValue = "18"
            )
    )
    private FlvScriptTagBody flvScriptTagBody;
}
