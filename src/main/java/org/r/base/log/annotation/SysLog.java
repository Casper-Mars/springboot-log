package org.r.base.log.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解 拦截记录操作日志
 **/
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SysLog {

    /**
     * 模块名称
     */
    String module() default "";

    /**
     * 操作类型
     */
    String operation() default "";

    /**
     * 详情
     */
    String msg() default "";

    /**
     * 提供查询信息的类
     */
    Class<?> infoProvider() default void.class;

    /**
     * 根据主键查询信息的方法名称
     */
    String method() default "";

    /**
     * 主键名称
     */
    String idName() default "";

    /**
     * 入参中的对象的主键名称
     */
    String keyName() default "id";

    /**
     * 占位符
     *
     * @return
     */
    String[] placeHolder() default {};


}
