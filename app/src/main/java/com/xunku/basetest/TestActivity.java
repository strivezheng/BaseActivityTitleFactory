package com.xunku.basetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xunku.basetest.ptrHelper.ptrConfig;
import com.xunku.basetest.view.testView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class TestActivity extends AppCompatActivity implements testView,ptrConfig {

    myPresent myPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        EventBus.getDefault().register(this);

        myPresent=new myPresent(this);
        myPresent.begin();

        String str="hello,world";
        char ch1=str.charAt(0);     //ch1  :  h
        char ch2=str.charAt(1);     //ch2  :  e
        char ch3=str.charAt(2);     //ch3  :  l

        String str2="hello,world";
        String[]  arr=str2.split(",",2);
        //arr[0]:hello
        //arr[1]:world


    }

    @Override
    public void initPtr(int num) {
        Log.i("sinstar", "initPtr: 列表展示了"+num);
        if(myPresent.hasMore());
    }

    @Override
    public void initDialog(String msg) {
        Log.i("sinstar", "initDialog: "+msg);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresent.release();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(String message){
        Log.i("sinstar", "helloEventBus: "+message);
    }

    @Override
    public boolean hasMore() {
        return false;
    }

    @Override
    public void reFresh() {

    }

    @Override
    public void loadMore() {

    }
}
