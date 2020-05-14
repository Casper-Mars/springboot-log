package org.r.base.log.adatper;

import org.r.base.log.wrapper.TaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * date 2020/5/14 13:28
 *
 * @author casper
 */
public class LogValueAdapterChain {

    private final Logger log = LoggerFactory.getLogger(LogValueAdapterChain.class);

    private final List<LogValueAdapter> adapterList;

    public LogValueAdapterChain(List<LogValueAdapter> adapterList) {
        this.adapterList = adapterList;
    }

    public String doChain(TaskWrapper taskWrapper, String placeHolder, String originValue) {
        if (!CollectionUtils.isEmpty(adapterList)) {
            for (LogValueAdapter adapter : adapterList) {
                try {
                    String value = adapter.decodeValue(taskWrapper, placeHolder, originValue);
                    if (value != null) {
                        return value;
                    }
                } catch (Exception e) {
                  log.error("there is a error when doing the log value chain and the msg is :"+e.getMessage());
                }
            }
        }
        return null;
    }


}
