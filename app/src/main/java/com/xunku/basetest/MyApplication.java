package com.xunku.basetest;

import android.app.Application;

/**
 * Created 郑贤鑫 on 2017/2/9.
 */

public class MyApplication extends Application {
    private static MyApplication _instance;

    public static MyApplication getInstance(){
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _instance=this;
    }
}
