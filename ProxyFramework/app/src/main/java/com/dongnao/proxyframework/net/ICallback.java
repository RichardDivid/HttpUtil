package com.dongnao.proxyframework.net;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface ICallback {
    void onSuccess(String result);

    void onFailure(int code,Throwable t);
}
