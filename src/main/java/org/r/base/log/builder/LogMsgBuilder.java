package org.r.base.log.builder;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * @author casper
 * @date 20-5-12 下午2:44
 **/
public interface LogMsgBuilder {


    /**
     * 构建日志消息
     *
     * @param task 任务信息
     * @return
     */
    String build(TaskWrapper task);


}
