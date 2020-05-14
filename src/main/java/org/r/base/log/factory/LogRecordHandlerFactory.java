package org.r.base.log.factory;

import org.r.base.log.handler.LogRecordHandler;
import org.r.base.log.wrapper.TaskWrapper;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * date 2020/5/14 20:12
 *
 * @author casper
 */
public class LogRecordHandlerFactory {

    private final Map<Class<?>, LogRecordHandler> handlerIndex;

    public LogRecordHandlerFactory(List<LogRecordHandler> logRecordHandlers) {
        if (CollectionUtils.isEmpty(logRecordHandlers)) {
            this.handlerIndex = new HashMap<>();
        } else {
            this.handlerIndex = logRecordHandlers.stream().collect(Collectors.toMap(LogRecordHandler::getTargetClass, t -> t, (k1, k2) -> k1));
        }
    }

    /**
     * 根据目标类获取对应的处理器
     *
     * @param targetClass 目标类
     * @return
     */
    public LogRecordHandler getHandler(Class<?> targetClass) {
        return handlerIndex.get(targetClass);
    }


}
