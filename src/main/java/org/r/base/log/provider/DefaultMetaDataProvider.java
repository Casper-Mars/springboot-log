package org.r.base.log.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * date 20-5-11 下午1:53
 *
 * @author casper
 **/
public class DefaultMetaDataProvider implements MetaDataProvider {
    /**
     * 获取处理的附加信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getMetaData() {
        return new HashMap<>();
    }
}
