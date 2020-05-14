package org.r.base.log.adatper;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 2020/5/14 13:13
 *
 * @author casper
 */
public interface LogValueAdapter {

    /**
     * 根据类和方法自定义特殊的值构造方式
     *
     * @param wrapper     上下文
     * @param placeHolder 需要特殊处理的值得占位符
     * @param originValue 从参数中提取出来的值
     * @return
     */
    String decodeValue(TaskWrapper wrapper, String placeHolder, String originValue);
}
