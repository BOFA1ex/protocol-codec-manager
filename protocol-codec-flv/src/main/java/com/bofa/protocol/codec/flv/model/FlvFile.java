package com.bofa.protocol.codec.flv.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.*;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.method.validate.UnicodeEqualsValidateMethod;
import lombok.Data;

import java.util.List;

/**
 * @author bofa1ex
 * @version 1.0
 * @since  2019/12/23
 */
@Data
@CacheMapping("flvFile")
@ByteBufValidation(
        validate = @ByteBufValidation.Validate(
                index = @ByteBufInternalPoint(),
                length = @ByteBufInternalPoint(step = "3")
        ),
        mapper = @ByteBufValidation.Mapper(
                index = @ByteBufInternalPoint(),
                length = @ByteBufInternalPoint(step = "3")
        ),
        validateMethod = UnicodeEqualsValidateMethod.class,
        parameters = {"FLV", UnicodeConvertMethod.CHARSET_NAME_US_ASCII}
)
public class FlvFile {

    private String signature = "FLV";
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "3"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer version;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"0", "4"}
    )
    private Integer typeFlagsReserved;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"4", "5"}
    )
    private Integer typeFlagsAudio;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"5", "6"}
    )
    private Integer typeFlagsReserved2;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "-1"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class,
            parameters = {"6", "7"}
    )
    private Integer typeFlagsVideo;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "4"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer dataOffset;
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type= ByteBufInternalPoint.StepType.REVERSE),
            parameters = {"java.util.LinkedList"}
    )
    private List<FlvTag> flvTags;
}
