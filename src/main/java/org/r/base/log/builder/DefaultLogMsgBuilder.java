package org.r.base.log.builder;

import com.alibaba.fastjson.JSONObject;
import org.r.base.log.annotation.SysLog;
import org.r.base.log.util.ReflectUtil;
import org.r.base.log.wrapper.TaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date 20-5-12 下午2:47
 *
 * @author casper
 **/
public class DefaultLogMsgBuilder implements LogMsgBuilder {
    private final Logger log = LoggerFactory.getLogger(DefaultLogMsgBuilder.class);

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
        String[] placeHolders = getAllPlaceHolder(msg);
        if (placeHolders.length == 0) {
            return msg;
        }
        Object[] paramters = task.getParamters();
        Map<String, String> valueIndex = valueArray(paramters, placeHolders);
        for (String placeHolder : placeHolders) {
            msg = msg.replace(placeHolder, valueIndex.get(placeHolder));
        }
        return msg;
    }

    private Map<String, String> valueArray(Object[] parameters, String[] placeHolders) {

        Map<String, String> index = new HashMap<>();
        for (String placeHolder : placeHolders) {
            String value = index.get(placeHolder);
            if (!StringUtils.isEmpty(value)) {
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

    private String decodePlaceHolder(String holder, Object[] value) {

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
        return parseToString(result);
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
        return ReflectUtil.getInstance().getAttrValue(target, attrName);
    }


    private String parseToString(Object object) {
        if (object instanceof Collection) {
            return JSONObject.toJSONString(object);
        }
        return object.toString();
    }


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
