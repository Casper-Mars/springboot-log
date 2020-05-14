package org.r.base.log;

import org.r.base.log.adatper.DefaultLogValueAdapter;
import org.r.base.log.adatper.LogValueAdapter;
import org.r.base.log.adatper.LogValueAdapterChain;
import org.r.base.log.aop.LogAop;
import org.r.base.log.builder.DefaultLogMsgBuilder;
import org.r.base.log.builder.LogMsgBuilder;
import org.r.base.log.factory.DefaultLogRecordFactory;
import org.r.base.log.factory.LogRecordFactory;
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

import java.util.List;

/**
 * date 20-5-8 下午5:39
 *
 * @author casper
 **/
@Configuration
public class LogFrameworkAutoConfig implements ApplicationContextAware {


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
    @ConditionalOnMissingBean
    public LogMsgBuilder logMsgBuilder(LogValueAdapterChain adapterChain) {
        return new DefaultLogMsgBuilder(adapterChain);
    }

    public LogRecordFactory logRecordFactory(){
        return new DefaultLogRecordFactory() {
            @Override
            public Class<?> getTargetClass() {
                return super.getTargetClass();
            }
        };
    }


    @Bean
    public LogAop logAop(TaskDelegate delegate, MetaDataProvider metaDataProvider, LogMsgBuilder logMsgBuilder) {
        System.out.println(".__                 \n" +
                "|  |   ____   ____  \n" +
                "|  |  /  _ \\ / ___\\ \n" +
                "|  |_(  <_> ) /_/  >\n" +
                "|____/\\____/\\___  / \n" +
                "           /_____/  ");
        return new LogAop(new LogTaskPool(), delegate, metaDataProvider, logMsgBuilder,null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("set up the application context");
        SpringUtil.setContext(applicationContext);
    }
}
