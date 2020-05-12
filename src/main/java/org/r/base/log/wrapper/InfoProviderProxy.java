package org.r.base.log.wrapper;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * date 20-5-8 下午3:46
 *
 * @author casper
 **/
public class InfoProviderProxy {


    /**
     * 执行查询操作的方法
     */
    private List<Method> selectMethod;

    /**
     * 执行方法的对象
     */
    private Object invokeObject;


    public InfoProviderProxy(Object invokeObject, List<Method> selectMethod) {
        this.selectMethod = selectMethod;
        this.invokeObject = invokeObject;
    }

    public Object invoke(Object... arg) {
        if (CollectionUtils.isEmpty(selectMethod)) {
            return null;
        }
        Object result = null;
        for (Method method : selectMethod) {
            try {
                if(arg != null){
                    if(method.getParameterCount()!=arg.length){
                        continue;
                    }
                    result = method.invoke(invokeObject, arg);
                }else {
                    if(method.getParameterCount()!=0){
                        continue;
                    }
                    result = method.invoke(invokeObject);
                }
                if (result != null) {
                    break;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }



}
