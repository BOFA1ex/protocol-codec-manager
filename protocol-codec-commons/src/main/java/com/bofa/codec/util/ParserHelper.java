package com.bofa.codec.util;

import com.bofa.commons.apt4j.annotate.protocol.Protocol;

import java.util.Optional;

/**
 * @author bofa1ex
 * @since 2020/3/17
 */
public class ParserHelper {

    public static <T> T getParser(Class<T> clazz) {
        try {
            return ParserContext.builder().processClazz(clazz).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(String.format("%s clazz initialize failed.", clazz));
        }
    }

    static class ParserContext {
        private static final String DOT = ".";
        private String parserImplName;
        private Class<?> parserImplClazz;

        static ParserContext builder() {
            return new ParserContext();
        }

        ParserContext processClazz(Class<?> clazz) {
            final String parserPackageQualifierName = clazz.getPackage().getName();
            this.parserImplName = clazz.getDeclaredAnnotation(Protocol.class).implName();
            try {
                this.parserImplClazz = Class.forName(parserPackageQualifierName + DOT + parserImplName);
            } catch (ClassNotFoundException ignore) {
            }
            return this;
        }

        <T> T newInstance() throws IllegalAccessException, InstantiationException {
            return (T) Optional.ofNullable(parserImplClazz)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("[%s] ClassNotFound", parserImplName)))
                    .newInstance();
        }
    }
}
