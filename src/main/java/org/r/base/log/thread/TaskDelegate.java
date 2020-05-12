package org.r.base.log.thread;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 20-5-8 下午4:57
 *
 * @author casper
 **/
public interface TaskDelegate {

    void run(TaskWrapper task);

}
