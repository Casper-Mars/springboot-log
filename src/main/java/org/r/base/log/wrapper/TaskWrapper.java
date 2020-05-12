package org.r.base.log.wrapper;


import org.r.base.log.annotation.SysLog;

import java.util.Map;

/**
 * date 20-5-8 下午4:58
 *
 * @author casper
 **/
public interface TaskWrapper {

    /**
     * 获取注解
     *
     * @return
     */
    SysLog getAnnotation();

    /**
     * 获取方法参数
     *
     * @return
     */
    Object[] getParamters();

    /**
     * 获取执行的结果
     *
     * @return
     */
    Object getResult();

    /**
     * 获取执行前的数据
     *
     * @return
     */
    Object getBefore();

    /**
     * 获取执行后的数据
     *
     * @return
     */
    Object getAfter();

    /**
     * 获取附加的数据
     *
     * @return
     */
    Map<String, Object> getMetaData();

    /**
     * 获取拦截的类
     *
     * @return
     */
    Class<?> getTargetClass();

    /**
     * 获取拦截的方法名称
     *
     * @return
     */
    String getTargetMethodName();

    /**
     * 获取日志消息
     *
     * @return
     */
    String getLogMsg();


}
