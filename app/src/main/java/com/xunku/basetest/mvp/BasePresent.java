package com.xunku.basetest.mvp;

import android.content.Context;

import com.xunku.base.nohttp.SBHttpListener;
import com.xunku.basetest.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 郑贤鑫 on 2017/2/9.
 */

public abstract class BasePresent implements SBHttpListener {

    protected BaseView baseView;

    protected MyApplication application;
    protected Context appContext;

    protected List<BaseModel> modelList;

    //代码块，先于构造方法执行
    {
//        application=MyApplication.getInstance();
//        appContext=application.getApplicationContext();
        modelList = new ArrayList<>();
    }


    public BasePresent(BaseView activityView){
        this.baseView=activityView;
    }

    protected void addModel(BaseModel model){
        modelList.add(model);
    }

    /**
     * 释放资源
     */
    public void release(){
        if(modelList != null){
            for(BaseModel model : modelList){
                if(model != null){
                    model.release();
                }
            }
            modelList.clear();
        }

    }

}
