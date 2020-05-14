package org.r.base.log.adatper;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 2020/5/14 13:52
 *
 * @author casper
 */
public class DefaultLogValueAdapter implements LogValueAdapter {

    @Override
    public String decodeValue(TaskWrapper wrapper, String placeHolder, String originValue) {
        return originValue;
    }
}
