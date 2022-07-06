package com.user.libbase.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

    /**
     * 通过反射获取泛型类型
     *
     * @param obj 对象
     * @return 对象类型
     */
    public static Class<?> analysisClassInfo(Object obj) {
        Type type = obj.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] params = parameterizedType.getActualTypeArguments();
        return (Class<?>) params[0];


    }
}
