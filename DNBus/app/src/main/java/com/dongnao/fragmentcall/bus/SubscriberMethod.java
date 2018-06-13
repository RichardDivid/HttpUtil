package com.dongnao.fragmentcall.bus;

import java.lang.reflect.Method;

/**
 * @author Lance
 * @date 2018/4/9
 */

public class SubscriberMethod {
    private String label;

    private Method method;

    private Class<?>[] parameterTypes;


    public SubscriberMethod(String label, Method method, Class<?>[] parameterTypes) {
        this.method = method;
        this.label = label;
        this.parameterTypes = parameterTypes;
    }

    public Method getMethod() {
        return method;
    }


    public String getLabel() {
        return label;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
}
