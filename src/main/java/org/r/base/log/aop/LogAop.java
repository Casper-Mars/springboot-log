package org.r.base.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.r.base.log.annotation.SysLog;
import org.r.base.log.builder.InfoProviderBuilder;
import org.r.base.log.builder.LogMsgBuilder;
import org.r.base.log.provider.MetaDataProvider;
import org.r.base.log.thread.LogTask;
import org.r.base.log.thread.LogTaskPool;
import org.r.base.log.thread.TaskDelegate;
import org.r.base.log.util.ReflectUtil;
import org.r.base.log.wrapper.DefaultTaskWrapper;
import org.r.base.log.wrapper.InfoProviderProxy;

/**
 * date 20-5-8 下午3:35
 *
 * @author casper
 **/
@Aspect
public class LogAop {

    private final LogTaskPool pool;
    private final TaskDelegate delegate;
    private final MetaDataProvider metaDataProvider;
    private final LogMsgBuilder logMsgBuilder;

    public LogAop(LogTaskPool pool, TaskDelegate delegate, MetaDataProvider metaDataProvider,LogMsgBuilder logMsgBuilder) {
        this.pool = pool;
        this.delegate = delegate;
        this.metaDataProvider = metaDataProvider;
        this.logMsgBuilder = logMsgBuilder;
    }

    @Pointcut(value = "@annotation(log)", argNames = "log")
    public void pointCut(SysLog log) {

    }

    @Around(value = "pointCut(log)", argNames = "point,log")
    public Object arount(ProceedingJoinPoint point, SysLog log) {

        /*获取拦截的类和对应的方法,和参数*/
        Class<?> targetClass = point.getTarget().getClass();
        String targetMethodName = point.getSignature().getName();
        Object[] args = point.getArgs();

        /*判断获取操作前后的数据*/
        boolean needInvoke = true;
        if (log.infoProvider() == void.class) {
            needInvoke = false;
        }
        Object beforeInvoke = null;
        Object afterInvoke = null;
        Object key = null;
        if (needInvoke) {
            if (args != null && args.length > 0) {
                key = getKey(log.keyName(), args[0]);
            }
            beforeInvoke = invoke(log, key);
        }

        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (needInvoke) {
            afterInvoke = invoke(log, key);
        }
        DefaultTaskWrapper wrapper = new DefaultTaskWrapper(log, targetClass, targetMethodName, args, proceed, beforeInvoke, afterInvoke, metaDataProvider.getMetaData(),logMsgBuilder);
        LogTask task = new LogTask(wrapper, delegate);
        pool.execute(task);
        return proceed;
    }


    private Object getKey(String keyName, Object arg) {
        return ReflectUtil.getInstance().getAttrValue(arg, keyName);
    }


    private Object invoke(SysLog log, Object... args) {
        InfoProviderProxy providerProxy = null;
        Object result = null;
        try {
            providerProxy = InfoProviderBuilder.build(log);
            if (providerProxy != null) {
                result = providerProxy.invoke(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
