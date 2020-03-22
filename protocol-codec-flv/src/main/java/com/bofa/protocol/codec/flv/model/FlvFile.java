package com.bofa.protocol.codec.flv.model;

import com.bofa.codec.method.convert.BinaryIntegerConvertMethod;
import com.bofa.codec.method.convert.IntegerConvertMethod;
import com.bofa.codec.method.validate.AsciiEqualsValidateMethod;
import com.bofa.codec.method.validate.IntegerEqualsValidateMethod;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufValidation;
import com.bofa.commons.apt4j.annotate.spel.SpelMapping;
import lombok.Data;

import java.util.List;

/**
 * @author bofa1ex
 * @version 1.0
 * @package com.bofa.prolengthcol.flv
 * @date 2019/12/23
 */
@Data
@SpelMapping("flvFile")
@ByteBufValidation(
        validate = @ByteBufValidation.Validate(index = "0", length = "3"),
        mapper = @ByteBufValidation.Mapper(index = "0", length = "3"),
        validateMethod = AsciiEqualsValidateMethod.class,
        parameters = {"FLV"},
        order = 1
)
@ByteBufValidation(
        validate = @ByteBufValidation.Validate(index = "#flvFile_buffer.readableBytes() - 4", length = "4"),
        mapper = @ByteBufValidation.Mapper(index = "#flvFile_buffer.readableBytes()", length = "4"),
        validateMethod = IntegerEqualsValidateMethod.class,
        parameters = {"1"},
        order = 2
)
public class FlvFile {
    private String signature = "FLV";
    @ByteBufConvert(index = "3", length = "1", convertMethod = IntegerConvertMethod.class)
    private Integer version;
    @ByteBufConvert(index = "4", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"0", "4"})
    private Integer typeFlagsReserved;
    @ByteBufConvert(index = "4", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"4", "5"})
    private Integer typeFlagsAudio;
    @ByteBufConvert(index = "4", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"5", "6"})
    private Integer typeFlagsReserved2;
    @ByteBufConvert(index = "4", length = "1", convertMethod = BinaryIntegerConvertMethod.class, parameters = {"6", "7"})
    private Integer typeFlagsVideo;
    @ByteBufConvert(index = "5", length = "4", convertMethod = IntegerConvertMethod.class)
    private Integer dataOffset;
    @ByteBufConvert(index = "9", length = "#flvFile_buffer.readableBytes()", parameters = {"java.util.LinkedList"})
    private List<FlvTag> flvTags;
    private Integer test = 1;
}
