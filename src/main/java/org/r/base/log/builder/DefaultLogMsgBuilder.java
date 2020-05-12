package org.r.base.log.builder;

import org.r.base.log.annotation.SysLog;
import org.r.base.log.util.ReflectUtil;
import org.r.base.log.wrapper.TaskWrapper;

/**
 * date 20-5-12 下午2:47
 *
 * @author casper
 **/
public class DefaultLogMsgBuilder implements LogMsgBuilder {
    /**
     * 构建日志消息
     *
     * @param task 任务信息
     * @return
     */
    @Override
    public String build(TaskWrapper task) {
        SysLog annotation = task.getAnnotation();
        String msg = annotation.msg();
        if ("".equals(msg)) {
            return "";
        }
        String[] placeHolders = annotation.placeHolder();
        if (placeHolders.length == 0) {
            return msg;
        }
        Object[] paramters = task.getParamters();
        String[] valueArray = valueArray(paramters, placeHolders);
        StringBuilder sb = new StringBuilder();
        int index = -1;
        int count = 0;
        while ((index = msg.indexOf("#{")) !=-1){
            int end = msg.indexOf("}");
            sb.append(msg, 0, index);
            sb.append(valueArray[count++]);
            msg = msg.substring(end+1);
        }
        sb.append(msg);
        if(sb.length() == 0){
            return msg;
        }
        return sb.toString();
    }

    private String[] valueArray(Object[] parameters, String[] placeHolders) {
        String[] result = new String[placeHolders.length];
        for (int i = 0; i < placeHolders.length; i++) {
            result[i] = decodePlaceHolder(placeHolders[i], parameters[i]);
        }
        return result;
    }

    private String decodePlaceHolder(String holder, Object value) {
        if ("".equals(holder)) {
            return value.toString();
        }
        return ReflectUtil.getInstance().getAttrValue(value, holder).toString();
    }


}
