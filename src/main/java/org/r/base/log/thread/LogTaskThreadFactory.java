package org.r.base.log.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * date 20-5-8 下午5:08
 *
 * @author casper
 **/
public class LogTaskThreadFactory implements ThreadFactory {

    private AtomicInteger num = new AtomicInteger(1);


    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "log-task-pool" + num.getAndIncrement());
    }
}
