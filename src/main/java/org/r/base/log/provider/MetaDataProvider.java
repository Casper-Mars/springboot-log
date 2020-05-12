package org.r.base.log.provider;

import java.util.Map;

/**
 * date 20-5-11 下午1:51
 *
 * @author casper
 **/
public interface MetaDataProvider {


    /**
     * 获取处理的附加信息
     *
     * @return
     */
    Map<String, Object> getMetaData();

}
