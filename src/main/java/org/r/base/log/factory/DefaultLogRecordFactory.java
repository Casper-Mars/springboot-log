package org.r.base.log.factory;

import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 2020/5/14 20:12
 *
 * @author casper
 */
public abstract class DefaultLogRecordFactory implements LogRecordFactory {

    @Override
    public Class<?> getTargetClass() {
        return null;
    }

    @Override
    public Object getModifyData(Object... parameters) {
        return null;
    }

    @Override
    public Object getValueFromParameter(Object target, String attrName) {
        return null;
    }

    @Override
    public String parseValueToString(TaskWrapper task, String placeHolder, Object originValue) {
        return null;
    }
}
