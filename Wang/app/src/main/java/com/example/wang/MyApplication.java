package com.example.wang;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by wang on 2015/7/7.
 */
public class MyApplication extends Application{

    public void onCreate(){
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
