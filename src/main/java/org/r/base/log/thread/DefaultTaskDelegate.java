package org.r.base.log.thread;

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

//        Map<String, Object> info = ((DefaultTaskWrapper)task).getInfo();
//        String s = JSONObject.toJSONString(info);
//        System.out.println(s);


    }
}
