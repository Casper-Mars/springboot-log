package org.r.base.log.thread;

import com.alibaba.fastjson.JSONObject;
import org.r.base.log.wrapper.TaskWrapper;
import org.springframework.stereotype.Component;

/**
 * date 20-5-8 下午4:59
 *
 * @author casper
 **/
@Component
public class DefaultTaskDelegate implements TaskDelegate {
    @Override
    public void run(TaskWrapper task) {

        String s = JSONObject.toJSONString(task);
        System.out.println(s);
    }
}
