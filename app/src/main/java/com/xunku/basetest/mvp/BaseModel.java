package com.xunku.basetest.mvp;

/**
 * Created 郑贤鑫 on 2017/2/21.
 */

public  interface BaseModel {

    /**
     * get 请求
     */
    public abstract void get();


    /**
     * post 请求
     */
    public abstract void post();


    /**
     * 释放资源
     */
    public abstract void release();
}
