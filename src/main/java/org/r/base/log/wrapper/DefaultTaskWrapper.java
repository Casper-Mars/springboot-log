package org.r.base.log.wrapper;


import org.r.base.log.annotation.SysLog;
import org.r.base.log.builder.LogMsgBuilder;

import java.util.Map;

/**
 * date 20-5-9 下午5:23
 *
 * @author casper
 **/
public class DefaultTaskWrapper implements TaskWrapper {


    private final SysLog sysLog;

    private final Object result;

    private final Object before;

    private final Object after;

    private final Object[] parameters;

    private final Class<?> targetClass;

    private final String targetMethodName;

    private final Map<String, Object> metaData;

    private String logMsg;

    private final LogMsgBuilder logMsgBuilder;

    public DefaultTaskWrapper(
            SysLog sysLog,
            Class<?> targetClass,
            String targetMethodName,
            Object[] parameters,
            Object result,
            Object before,
            Object after,
            Map<String, Object> metaData,
            LogMsgBuilder logMsgBuilder) {
        this.sysLog = sysLog;
        this.parameters = parameters;
        this.result = result;
        this.before = before;
        this.after = after;
        this.metaData = metaData;
        this.targetClass = targetClass;
        this.targetMethodName = targetMethodName;
        this.logMsgBuilder = logMsgBuilder;
    }

    /**
     * 获取注解
     *
     * @return
     */
    @Override
    public SysLog getAnnotation() {
        return sysLog;
    }

    /**
     * 获取方法参数
     *
     * @return
     */
    @Override
    public Object[] getParamters() {
        return parameters;
    }

    /**
     * 获取执行的结果
     *
     * @return
     */
    @Override
    public Object getResult() {
        return result;
    }

    /**
     * 获取执行前的数据
     *
     * @return
     */
    @Override
    public Object getBefore() {
        return before;
    }

    /**
     * 获取执行后的数据
     *
     * @return
     */
    @Override
    public Object getAfter() {
        return after;
    }

    /**
     * 获取附加的数据
     *
     * @return
     */
    @Override
    public Map<String, Object> getMetaData() {
        return metaData;
    }

    /**
     * 获取拦截的类
     *
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * 获取拦截的方法名称
     *
     * @return
     */
    @Override
    public String getTargetMethodName() {
        return targetMethodName;
    }

    /**
     * 获取日志消息
     *
     * @return
     */
    @Override
    public String getLogMsg() {
        if (this.logMsg == null || this.logMsg.isEmpty()) {
            this.logMsg = logMsgBuilder.build(this);
        }
        return this.logMsg;
    }
}
