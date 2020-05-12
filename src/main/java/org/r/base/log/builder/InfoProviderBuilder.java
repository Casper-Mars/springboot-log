package org.r.base.log.builder;


import org.r.base.log.annotation.SysLog;
import org.r.base.log.annotation.SysLogSelectMethod;
import org.r.base.log.util.SpringUtil;
import org.r.base.log.wrapper.InfoProviderProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * date 20-5-8 下午3:45
 *
 * @author casper
 **/
public class InfoProviderBuilder {

    private final static Map<Class<?>, InfoProviderProxy> pool = new HashMap<>();


    public static InfoProviderProxy build(SysLog sysLog) {
        if (sysLog.infoProvider() == void.class) {
            return null;
        }
        InfoProviderProxy infoProviderProxy = pool.get(sysLog.infoProvider());
        if (infoProviderProxy == null) {
            synchronized (InfoProviderBuilder.class) {
                infoProviderProxy = pool.get(sysLog.infoProvider());
                if (infoProviderProxy == null) {
                    infoProviderProxy = construct(sysLog.infoProvider());
                    pool.put(sysLog.infoProvider(), infoProviderProxy);
                }
            }
        }
        return infoProviderProxy;
    }

    private static InfoProviderProxy construct(Class<?> clazz) {
        Object bean = SpringUtil.getBean(clazz);
        if (bean == null) {
            throw new RuntimeException("can not find bean for class: " + clazz.getName());
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();

        List<Method> methods = Stream.of(declaredMethods).filter(t -> {
            SysLogSelectMethod annotation = t.getAnnotation(SysLogSelectMethod.class);
            return annotation != null;
        }).collect(Collectors.toList());
        return new InfoProviderProxy(bean, methods);
    }


}
