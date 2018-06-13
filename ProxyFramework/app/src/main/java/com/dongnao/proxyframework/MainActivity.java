package com.dongnao.proxyframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dongnao.proxyframework.model.WeatherInfo;
import com.dongnao.proxyframework.net.HttpRequestPresenter;
import com.dongnao.proxyframework.net.ICallback;
import com.dongnao.proxyframework.net.ModelCallback;
import com.dongnao.proxyframework.net.async.AsyncHttpRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        http://restapi.amap.com/v3/weather/weatherInfo
//        city=长沙
//        key=13cb58f5884f9749287abbead9c658f2


        HttpRequestPresenter.init(new AsyncHttpRequest());

        //json
        Map<String,String> params = new HashMap<>();
        params.put("city","长沙");
        params.put("key","13cb58f5884f9749287abbead9c658f2");
        HttpRequestPresenter.getInstance().get("http://restapi.amap.com/v3/weather/weatherInfo"
                , params, new ModelCallback<WeatherInfo>() {
                    @Override
                    public void onSuccess(WeatherInfo weatherInfo) {
                        Log.e(TAG,weatherInfo.toString());
                    }

                    @Override
                    public void onFailure(int code, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

}
