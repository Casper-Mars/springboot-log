package org.r.base.log.util;

import org.springframework.context.ApplicationContext;

/**
 * date 20-5-8 下午3:50
 *
 * @author casper
 **/
public class SpringUtil {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }


}
