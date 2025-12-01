package org.jeecg.modules.zxecg.phoenix.annotation;

import java.lang.annotation.*;

/**
 * @Description: Phoenix实体注解
 */
public class PhoenixEntityAnnotation implements Annotation {
    /**
     * 注解类
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ClassAnnotation {
        String table() default "";
        String[] indexs() default {};//index数组，格式：{"indexname1,column1,column2...","indexname2,column1,column2..."}
    }
    /**
     * 构造方法注解
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.CONSTRUCTOR)
    public @interface ConstructorAnnotation {
        String uri() default "";
        String desc() default "";
    }
    /**
     * 字段注解定义
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldAnnotation {
        String column() default "";
        int pkNum() default 0;
        int length() default 0;
        String sequence() default "";
        //列簇
        String cf() default "";
    }
    /**
     * 方法注解
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MethodAnnotation {
        String uri();
        String desc();
    }
    /**
     * 可以同时应用到类上和方法上
     */
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Yts {
        // 定义枚举
        public enum YtsType {
            util, entity, service, model
        }

        // 设置默认值
        public YtsType classType() default YtsType.util;

        // 数组
        int[] arr() default {3, 7, 5};

        String color() default "blue";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
