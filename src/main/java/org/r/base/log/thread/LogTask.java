package org.r.base.log.thread;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 20-5-8 下午4:56
 *
 * @author casper
 **/
public class LogTask implements Runnable {

    private final TaskWrapper taskWrapper;
    private TaskDelegate delegate;



    public LogTask(TaskWrapper taskWrapper, TaskDelegate delegate) {
        this.taskWrapper = taskWrapper;
        this.delegate = delegate;
    }

    public void setDelegate(TaskDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        if(delegate != null){
            delegate.run(taskWrapper);
        }
    }
}
