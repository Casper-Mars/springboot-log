package org.r.base.log.thread;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * date 20-5-8 下午4:54
 *
 * @author casper
 **/
public class LogTaskPool extends ThreadPoolExecutor implements Closeable {


    public LogTaskPool() {
        super(
                10,
                15,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                new LogTaskThreadFactory()
        );
    }

    @Override
    public void close() throws IOException {
        try {
            awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            shutdown();
        }
    }
}
