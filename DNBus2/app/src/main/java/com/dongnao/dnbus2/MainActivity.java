package com.dongnao.dnbus2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dongnao.dnbus2.bus.DNBus;
import com.dongnao.dnbus2.bus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DNBus.getDefault().register(this);
        //
//        DNBus.getDefault().post("1","我要去大保健1","我要去大保健2");
        //Fragment1 切换到首页
        DNBus.getDefault().post("main");
    }

    @Subscribe({"main"})
    private void test(){
        //Fragment2
        Log.e(TAG,"显示首页");
    }

    @Subscribe({"1","2"})
    private void test(String msg,Integer msg1){
        Log.e(TAG,"test   msg:"+msg+" msg1:"+msg1);
    }

    @Subscribe({"1"})
    private  void test2(String msg,String msg1){
        Log.e(TAG,"test2   msg:"+msg+" msg1:"+msg1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DNBus.getDefault().unregister(this);
    }
}
