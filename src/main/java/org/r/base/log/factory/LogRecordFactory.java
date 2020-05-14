package org.r.base.log.factory;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 2020/5/14 19:33
 *
 * @author casper
 */
public interface LogRecordFactory {


    /**
     * 获取拦截要处理的类
     *
     * @return
     */
    Class<?> getTargetClass();

    /**
     * 获取操作的数据
     *
     * @param parameters 接口的参数
     * @return
     */
    Object getModifyData(Object... parameters);


    /**
     * 从对象中获取指定属性的的值
     *
     * @param target   对象
     * @param attrName 属性名称
     * @return
     */
    Object getValueFromParameter(Object target, String attrName);


    /**
     * 转化值未字符串
     *
     * @param task        上下文
     * @param placeHolder 占位符
     * @param originValue 原始值
     * @return
     */
    String parseValueToString(TaskWrapper task, String placeHolder, Object originValue);

}
