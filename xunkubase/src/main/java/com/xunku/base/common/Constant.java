package com.xunku.base.common;

/**
 * Created 郑贤鑫 on 2017/1/5.
 *
 * 项目中的常量池
 */

public class Constant {

    /**
     * 请求成功，返回不正确code   000000
     */
    public static final int WRONG_TYPE_1=1;
    /**
     * 请求成功，，返回正确code，解析jsonResult异常
     */
    public static final int WRONG_TYPE_2=2;
    /**
     * 请求失败，服务器返回 错误responseCode 404
     */
    public static final int WRONG_TYPE_3=3;
    /**
     * 服务器无响应  比如没网   比如超过  3 s
     */
    public static final int WRONG_TYPE_4=4;

    /**
     * 没网
     */
    public static final int WRONG_TYPE_5=5;

    //各种case
    public static final int CASE_0=0;
    public static final int CASE_1=1;
    public static final int CASE_2=2;
    public static final int CASE_3=3;
    public static final int CASE_4=4;
    public static final int CASE_5=5;
    public static final int CASE_6=6;
    public static final int CASE_7=7;

    public static final int JSON_TO_BEAN=0;
    public static final int JSON_TO_LIST=1;
    public static final int JSON_TO_NONE=-1;



}
