package org.r.base.log.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * date 20-4-10 上午10:22
 *
 * @author casper
 **/
public class ReflectUtil {


    private static ReflectUtil reflectUtil = new ReflectUtil();

    private ReflectUtil() {

    }

    public static ReflectUtil getInstance() {
        return reflectUtil;
    }


    public <T> Object getAttrValue(T target, String attrName) {
        Class<?> clazz = target.getClass();
        String getMethod = getMethod(attrName);
        Object result = null;
        try {
            Method method = clazz.getMethod(getMethod);
            result = method.invoke(target);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取对象的属性名称和值
     *
     * @param target   需要获取的对象
     * @param skipNull 是否跳过值为null的属性，为true时跳过，为false时，null值转成空串
     * @param <T>      泛型参数
     * @return 属性名称为key，值为value的map
     */
    public <T> Map<String, Object> getAttrMap(T target, boolean skipNull) {
        Class<?> clazz = target.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields.length == 0 || declaredFields[0] == null) {
            return new HashMap<>();
        }
        /*提前定义好容器大小，避免扩容rehash*/
        int initialCapacity = (int) (declaredFields.length * 0.75 + 1);
        Map<String, Object> result = new HashMap<>(initialCapacity);
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            String getMethod = getMethod(name);
            try {
                Method method = clazz.getMethod(getMethod);
                Object invoke = method.invoke(target);
                if (invoke != null) {
                    result.put(name, invoke);
                } else if (!skipNull) {
                    result.put(name, "");
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    private String getMethod(String attrName) {
        return attrMethod("get", attrName);
    }

    private String setMethod(String attrName) {
        return attrMethod("set", attrName);
    }

    private String attrMethod(String prefix, String attrName) {
        return prefix + uppperFristChar(attrName);
    }

    private String uppperFristChar(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] -= 32;
        }

        return new String(chars);
    }


}
