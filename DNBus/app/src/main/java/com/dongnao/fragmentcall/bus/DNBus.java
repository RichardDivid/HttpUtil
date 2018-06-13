package com.dongnao.fragmentcall.bus;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Lance
 * @date 2018/4/9
 */

public class DNBus {

    private static DNBus defaultInstance;
    /**
     */
    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new
            HashMap<>();

    /**
     * 订阅集合
     * 发送事件的时候 通过Key(标签)查找所有对应的订阅者
     * key 为 订阅的标签 , value为 [订阅者(函数所在的对象)、[订阅的标签、订阅者(函数)、(函数)参数]]
     */
    private static final Map<String, List<Subscription>> SUBSCRIBES = new
            HashMap<>();

    /**
     * 对应对象中所有需要回调的标签 方便注销
     * key是订阅者(函数)所在类对象， value是该类中所有的订阅标签
     */
    private static final Map<Class<?>, List<String>> REGISTERS = new
            HashMap<>();

    public static DNBus getDefault() {
        if (defaultInstance == null) {
            synchronized (DNBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new DNBus();
                }
            }
        }
        return defaultInstance;
    }

    public void clear() {
        METHOD_CACHE.clear();
        SUBSCRIBES.clear();
        REGISTERS.clear();
    }

    /**
     * 先删除注册集合 获得该对象所有的订阅标签
     * 查找订阅集合 删除对应对象上的订阅者
     *
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        //找到该对象中所有的订阅标签
        List<String> lables = REGISTERS.remove(subscriber.getClass());
        if (null != lables) {
            for (String lable : lables) {
                //根据标签查找记录
                List<Subscription> subscriptions = SUBSCRIBES.get(lable);
                if (null != subscriptions) {
                    Iterator<Subscription> iterator = subscriptions.iterator();
                    while (iterator.hasNext()) {
                        Subscription subscription = iterator.next();
                        //对象是同一个 则删除
                        if (subscription.getSubscriber() == subscriber) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    public void register(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();
        //找到被Subscribe注解的函数 并记录缓存
        List<SubscriberMethod> subscriberMethods = findSubscribe(subscriberClass);

        //为了方便注销
        List<String> labels = REGISTERS.get(subscriberClass);
        if (null == labels) {
            labels = new ArrayList<>();
        }

        //加入注册集合  key:标签 value:对应标签的所有函数
        for (SubscriberMethod subscriberMethod : subscriberMethods) {
            String label = subscriberMethod.getLabel();
            if (!labels.contains(label)) {
                labels.add(label);
            }
            List<Subscription> subscriptions = SUBSCRIBES.get(label);
            if (subscriptions == null) {
                subscriptions = new ArrayList<>();
                SUBSCRIBES.put(label, subscriptions);
            }
            Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
            subscriptions.add(newSubscription);
        }

        REGISTERS.put(subscriberClass, labels);
    }

    /**
     * 找到被Subscribe注解的函数 并记录缓存
     *
     * @param subscriberClass
     */
    private List<SubscriberMethod> findSubscribe(Class<?> subscriberClass) {
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (null == subscriberMethods) {
            subscriberMethods = new ArrayList<>();
            //遍历函数
            Method[] methods = subscriberClass.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                if (null != subscribeAnnotation) {
                    //注解上的标签
                    String[] values = subscribeAnnotation.value();
                    //参数
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (String value : values) {
                        method.setAccessible(true);
                        SubscriberMethod subscriberMethod = new SubscriberMethod(value, method,
                                parameterTypes);
                        subscriberMethods.add(subscriberMethod);
                    }
                }
            }
            //缓存
            METHOD_CACHE.put(subscriberClass, subscriberMethods);
        }
        return subscriberMethods;
    }

    /**
     * 发送事件给所有订阅者
     *
     * @param label
     * @param params
     */
    public void post(String label, Object... params) {
        //获得所有对应的订阅者
        List<Subscription> subscriptions = SUBSCRIBES.get(label);
        for (Subscription subscription : subscriptions) {
            //组装参数 执行函数
            SubscriberMethod subscriberMethod = subscription.getSubscriberMethod();
            Class<?>[] parameterTypes = subscriberMethod.getParameterTypes();
            Object[] realParams = new Object[parameterTypes.length];
            if (null != params) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i < params.length && parameterTypes[i].isInstance(params[i])) {
                        realParams[i] = params[i];
                    } else {
                        realParams[i] = null;
                    }
                }
            }
            try {
                subscriberMethod.getMethod().invoke(subscription.getSubscriber(), realParams);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}
