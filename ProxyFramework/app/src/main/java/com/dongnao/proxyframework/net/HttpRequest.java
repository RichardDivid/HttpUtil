package com.dongnao.proxyframework.net;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface HttpRequest {

    void get(String url, Map<String,String> params,ICallback callback);

    void post(String url, Map<String,String> params,ICallback callback);
}
