package com.xunku.base.nohttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created 郑贤鑫 on 2017/1/5.
 */

public interface SBHttpListener<T>  {
    /**
     * 只管你请求成功的逻辑 干你该干的事，解析，展示
     * @param what
     * @param
     */
    void httpSuccess(int what, Object object, List<T> list, JSONObject jsonResult) throws JSONException;

    /**
     * 所有错误的集合
     * @param what
     * @param wrong
     * @param jsonResult
     *  /**


     请求成功，返回不正确code   000000
     WRONG_TYPE_1=1;

    请求成功，，返回正确code，解析jsonResult异常
    WRONG_TYPE_2=2;

    请求失败，服务器返回 错误responseCode 404
    WRONG_TYPE_3=3;

    服务器无响应  比如没网   比如超过  3 s
    public static final int WRONG_TYPE_4=4;

     */
    void httpFailed(int what, int wrong, JSONObject jsonResult) throws JSONException;
}
