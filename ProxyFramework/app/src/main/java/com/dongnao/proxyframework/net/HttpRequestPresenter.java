package com.dongnao.proxyframework.net;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public class HttpRequestPresenter implements HttpRequest {

    private HttpRequest httpRequest;
    private static volatile HttpRequestPresenter instance;

    private HttpRequestPresenter(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static void init(HttpRequest httpRequest) {
        if (null == instance){
            synchronized (HttpRequestPresenter.class){
                if (null == instance){
                    instance = new HttpRequestPresenter(httpRequest);
                }
            }
        }
    }

    public static HttpRequestPresenter getInstance() {
        return instance;
    }

    @Override
    public void get(String url, Map<String, String> params, ICallback callback) {
        httpRequest.get(url,params,callback);
    }

    @Override
    public void post(String url, Map<String, String> params, ICallback callback) {
        httpRequest.post(url,params,callback);
    }
}
