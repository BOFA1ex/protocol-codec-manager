package com.bofa.codec.util;

import com.google.common.base.Strings;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * @author bofa1ex
 * @version 1.0
 * @package com.bofa.spel.util
 * @date 2020/1/15
 */
public class ChannelSpelContextUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();

    private static final StampedLock lock = new StampedLock();

    public static final AttributeKey<EvaluationContext> SPEL_CONTEXT = AttributeKey.valueOf("SPEL_CONTEXT");

//    public static final AttributeKey<Keys> keys = AttributeKey.valueOf("KEYS");

    /**
     * 适合读写场景中读多的情况, event though one channel per eventLoop...
     */
    private static EvaluationContext _getContext(Channel channel) {
        long stamp = lock.readLock();
        EvaluationContext context = channel.attr(SPEL_CONTEXT).get();
        try {
            while (context == null) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    context = new StandardEvaluationContext();
                    channel.attr(SPEL_CONTEXT).set(context);
                    break;
                } else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                }
            }
        } finally {
            lock.unlock(stamp);
        }
        return context;
    }

    /**
     * 执行expression表达
     * @param expr 表达式
     * @param channel 通道
     */
    public static void processExpr(String expr, Channel channel) {
        EvaluationContext context = _getContext(channel);
        parser.parseExpression(expr).getValue(context);
    }

    /**
     * 执行expression表达并获取结果
     * @param clazz 指定结果的class类型
     * @param channel 通道
     * @param expr 表达式
     * @return 表达式执行后的结果
     */
    public static <T> T processExprAndGet(String expr, Channel channel, Class<T> clazz) {
        if (Strings.isNullOrEmpty(expr)) {
            throw new RuntimeException("expr不可为空");
        }
        EvaluationContext context = _getContext(channel);
        return parser.parseExpression(expr).getValue(context, clazz);
    }

    /**
     * 注入变量
     * @param variable 变量名
     * @param obj 变量值
     * @param channel 通道
     */
    public static void setVariable(String variable, Object obj, Channel channel) {
        if (checkVariableNameValid(variable)) {
            throw new RuntimeException(String.format("variableName '%s' is not valid", variable));
        }
        EvaluationContext context = _getContext(channel);
        context.setVariable(variable, obj);
    }

    /**
     * 根据channel获取EvaluationContext, 并获取EvaluationContext中的variables缓存
     * @param channel 通道
     * @return EvaluationContext中的variables缓存
     */
    public static Map<String, Object> getVariables(Channel channel) {
        EvaluationContext context = _getContext(channel);
        try {
            Field variablesField = context.getClass().getDeclaredField("variables");
            variablesField.setAccessible(true);
            return (Map<String, Object>) variablesField.get(context);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
        return null;
    }

    /**
     * 检查变量名是否规范
     * @param variable 变量名
     * @return true/false
     */
    private static boolean checkVariableNameValid(String variable) {
        return variable.equals("root") || variable.startsWith("@");
    }
}
