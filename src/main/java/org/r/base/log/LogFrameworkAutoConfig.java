package org.r.base.log;

import org.r.base.log.adatper.DefaultLogValueAdapter;
import org.r.base.log.adatper.LogValueAdapter;
import org.r.base.log.adatper.LogValueAdapterChain;
import org.r.base.log.aop.LogAop;
import org.r.base.log.builder.LogMsgBuilder;
import org.r.base.log.factory.LogRecordHandlerFactory;
import org.r.base.log.handler.LogRecordHandler;
import org.r.base.log.provider.DefaultMetaDataProvider;
import org.r.base.log.provider.MetaDataProvider;
import org.r.base.log.thread.DefaultTaskDelegate;
import org.r.base.log.thread.LogTaskPool;
import org.r.base.log.thread.TaskDelegate;
import org.r.base.log.util.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * date 20-5-8 下午5:39
 *
 * @author casper
 **/
@Configuration
public class LogFrameworkAutoConfig implements ApplicationContextAware {


    private ApplicationContext applicationContext;


    @Bean
    @ConditionalOnMissingBean
    public TaskDelegate taskDelegate() {
        return new DefaultTaskDelegate();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogValueAdapter logValueAdapter() {
        return new DefaultLogValueAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogValueAdapterChain logValueAdapterChain(List<LogValueAdapter> logValueAdapters) {
        return new LogValueAdapterChain(logValueAdapters);
    }


    @Bean
    @ConditionalOnMissingBean
    public MetaDataProvider metaDataProvider() {
        return new DefaultMetaDataProvider();
    }

    @Bean
    public LogAop logAop(TaskDelegate delegate, MetaDataProvider metaDataProvider) {
        System.out.println(".__                 \n" +
                "|  |   ____   ____  \n" +
                "|  |  /  _ \\ / ___\\ \n" +
                "|  |_(  <_> ) /_/  >\n" +
                "|____/\\____/\\___  / \n" +
                "           /_____/  ");

        Map<String, LogRecordHandler> beans = applicationContext.getBeansOfType(LogRecordHandler.class);
        Collection<LogRecordHandler> values = beans.values();
        LogRecordHandlerFactory handlerFactory = new LogRecordHandlerFactory(new ArrayList<>(values));
        return new LogAop(new LogTaskPool(), delegate, metaDataProvider, handlerFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("set up the application context");
        this.applicationContext = applicationContext;
        SpringUtil.setContext(applicationContext);
    }
}
