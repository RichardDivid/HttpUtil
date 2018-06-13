package com.dongnao.fragmentcall.bus;

/**
 * @author Lance
 * @date 2018/4/9
 */

public class Subscription {

    private Object subscriber;
    private SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public SubscriberMethod getSubscriberMethod() {
        return subscriberMethod;
    }
}
