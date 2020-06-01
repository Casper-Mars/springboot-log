package org.r.base.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.r.base.log.annotation.SysLog;
import org.r.base.log.builder.LogMsgBuilder;
import org.r.base.log.factory.LogRecordHandlerFactory;
import org.r.base.log.handler.LogRecordHandler;
import org.r.base.log.provider.MetaDataProvider;
import org.r.base.log.thread.LogTask;
import org.r.base.log.thread.LogTaskPool;
import org.r.base.log.thread.TaskDelegate;
import org.r.base.log.wrapper.DefaultTaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date 20-5-8 下午3:35
 *
 * @author casper
 **/
@Aspect
public class LogAop {

    private final Logger log = LoggerFactory.getLogger(LogAop.class);

    private final LogTaskPool pool;
    private final TaskDelegate delegate;
    private final MetaDataProvider metaDataProvider;
    private final LogRecordHandlerFactory handlerFactory;


    public LogAop(
            LogTaskPool pool,
            TaskDelegate delegate,
            MetaDataProvider metaDataProvider,
            LogRecordHandlerFactory handlerFactory) {
        this.pool = pool;
        this.delegate = delegate;
        this.metaDataProvider = metaDataProvider;
        this.handlerFactory = handlerFactory;
    }

    @Pointcut(value = "@annotation(log)", argNames = "log")
    public void pointCut(SysLog log) {

    }

    @Around(value = "pointCut(log)", argNames = "point,log")
    public Object arount(ProceedingJoinPoint point, SysLog log) throws Throwable {

        /*获取拦截的类和对应的方法,和参数*/
        Class<?> targetClass = point.getTarget().getClass();
        String targetMethodName = point.getSignature().getName();
        Object[] args = point.getArgs();

        /*判断获取操作前后的数据*/
        Object beforeInvoke = null;
        Object afterInvoke = null;
        beforeInvoke = invoke(targetClass, targetMethodName, args);
        /*处理业务*/
        Object proceed = null;
        proceed = point.proceed();
        /*获取操作后的数据*/
        afterInvoke = invoke(targetClass, targetMethodName, args);
        try {
            DefaultTaskWrapper wrapper = new DefaultTaskWrapper(
                    log,
                    targetClass,
                    targetMethodName,
                    args,
                    proceed,
                    beforeInvoke,
                    afterInvoke,
                    metaDataProvider.getMetaData(),
                    new LogMsgBuilder(handlerFactory.getHandler(targetClass)));
            LogTask task = new LogTask(wrapper, delegate);
            pool.execute(task);
        } catch (Exception e) {
            this.log.info("get exception");
        }
        return proceed;
    }

    private Object invoke(Class<?> targetClass, String method, Object... args) {
        Object result = null;
        LogRecordHandler handler = handlerFactory.getHandler(targetClass);
        if (handler != null) {
            try {
                result = handler.getModifyData(method, args);
            } catch (Exception e) {
                log.error("can not get the modify data for class:" + targetClass.getName() + " with the error msg:" + e.getMessage());
            }
        }
        return result;
    }


}
