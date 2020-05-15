package org.r.base.log.builder;

import com.alibaba.fastjson.JSONObject;
import org.r.base.log.annotation.SysLog;
import org.r.base.log.handler.LogRecordHandler;
import org.r.base.log.util.ReflectUtil;
import org.r.base.log.wrapper.TaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date 20-5-12 下午2:47
 *
 * @author casper
 **/
public final class LogMsgBuilder {
    private final Logger log = LoggerFactory.getLogger(LogMsgBuilder.class);

    private final LogRecordHandler recordHandler;

    public LogMsgBuilder(LogRecordHandler recordHandler) {
        this.recordHandler = recordHandler;
    }

    /**
     * 构建日志消息
     *
     * @param task 任务信息
     * @return
     */
    public String build(TaskWrapper task) {
        SysLog annotation = task.getAnnotation();
        String msg = annotation.msg();
        if ("".equals(msg)) {
            return "";
        }
        String[] placeHolders = getAllPlaceHolder(msg);
        if (placeHolders.length == 0) {
            return msg;
        }
        Object[] parameters = task.getParamters();
        Map<String, Object> valueIndex = processValue(parameters, placeHolders);
        for (String placeHolder : placeHolders) {
            msg = msg.replace(placeHolder, decodeValue(task, placeHolder, valueIndex.get(placeHolder)));
        }
        return msg;
    }

    /**
     * 进一步解析数据
     *
     * @param task  上下文
     * @param value 数据
     * @return
     */
    private String decodeValue(TaskWrapper task, String placeHolder, Object value) {
        String result = null;
        if (recordHandler != null) {
            result = recordHandler.parseValueToString(task, placeHolder, value);
        }
        if (result == null) {
            return parseToString(value);
        }
        return result;
    }


    private Map<String, Object> processValue(Object[] parameters, String[] placeHolders) {

        Map<String, Object> index = new HashMap<>();
        for (String placeHolder : placeHolders) {
            Object value = index.get(placeHolder);
            if (value != null) {
                continue;
            }
            try {
                value = decodePlaceHolder(placeHolder, parameters);
            } catch (Exception e) {
                log.warn("something went worry when decoding the place holder:" + placeHolder);
                value = "";
            }
            index.put(placeHolder, value);
        }
        return index;
    }

    private Object decodePlaceHolder(String holder, Object[] value) {

        String attrName = holder.substring(2, holder.length() - 1);
        int index = attrName.indexOf(".");
        int attrIndex = -1;
        Object result;
        if (index == -1) {
            attrIndex = Integer.parseInt(attrName);
            result = value[attrIndex - 1];
        } else {
            attrIndex = Integer.parseInt(attrName.substring(0, index));
            attrName = attrName.substring(index + 1);
            result = getValue(attrName, value[attrIndex - 1]);
        }
        return result;
    }

    private Object getValue(String attrChain, Object value) {
        int index = attrChain.indexOf(".");
        String attrName;
        if (index == -1) {
            attrName = attrChain;
            return buildValue(value, attrName);
        } else {
            attrName = attrChain.substring(0, index);
            attrChain = attrChain.substring(index + 1);
            return getValue(attrChain, buildValue(value, attrName));
        }
    }


    /**
     * 子类钩子方法，继承的子类可以自定义获取值的方式
     *
     * @param target   操作的对象
     * @param attrName 属性名称
     * @return
     */
    protected Object buildValue(Object target, String attrName) {
        Object value = recordHandler.getValueFromParameter(target, attrName);
        if (value == null) {
            value = ReflectUtil.getInstance().getAttrValue(target, attrName);
        }
        return value;
    }

    /**
     * 对象转化为字符串
     *
     * @param object 对象
     * @return
     */
    private String parseToString(Object object) {
        return object instanceof Collection ? JSONObject.toJSONString(object) : object.toString();
    }

    /**
     * 根据正则切割出所有的占位符
     *
     * @param msg 目标字符串
     * @return
     */
    private String[] getAllPlaceHolder(String msg) {
        String reg = "(#\\{[\\w|\\d|\\.]*\\})";
        Pattern pattern = Pattern.compile(reg);
        List<String> result = new LinkedList<>();
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            String group = matcher.group();
            result.add(group);
        }
        return result.toArray(new String[]{});
    }


}
