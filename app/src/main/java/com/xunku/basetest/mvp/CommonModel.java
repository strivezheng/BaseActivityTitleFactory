package com.xunku.basetest.mvp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xunku.base.common.Constant;
import com.xunku.base.common.util.GsonControl;
import com.xunku.base.common.util.JsonControl;
import com.xunku.base.config.SysConfig;
import com.xunku.base.nohttp.CallServer;
import com.xunku.base.nohttp.HttpListener;
import com.xunku.base.nohttp.SBHttpListener;
import com.xunku.base.utils.DataUtil;
import com.xunku.basetest.MyApplication;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xunku.base.common.Constant.JSON_TO_BEAN;
import static com.xunku.base.common.Constant.JSON_TO_LIST;
import static com.xunku.base.common.Constant.JSON_TO_NONE;


/**
 * Created 郑贤鑫 on 2017/1/5.
 */

public class CommonModel implements BaseModel{
    private static CommonModel instance;

    private Request<String> request;
    private JSONObject jsonResult = null;
    private SBHttpListener sbListener;

    private Context context;
    private String url;
    private int what;

    private int type;
    private Class<?> clazz;
    //参数集合
    private Map<String, String> paramMaps = new HashMap<>();
    //取得 onceToken
    private MyApplication application;

    /**
     * 存放 what ， Map《type，class》
     */
    private Map<Integer,Map> whatMapMaps=new HashMap<>();


    private CommonModel(){}
    private static CommonModel getInstance(){
        if(instance == null){
            instance=new CommonModel();
        }
        return instance;
    }

    /**
     * get请求
     */
    @Override
    public void get(){
        Map<Integer,Class<?>> typeClassMaps=new HashMap<>();
        typeClassMaps.put(type,clazz);

        whatMapMaps.put(what,typeClassMaps);
//        if(!isNetworkAvailable((Activity) context)){
//            if(sbListener != null){
//                sbListener.httpFailed(what,Constant.WRONG_TYPE_1,jsonResult);
//            }
//            return;
//        }


        request = NoHttp.createStringRequest(SysConfig.SERVER_HOST_ADDRESS + url, RequestMethod.GET);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        if (request != null) {
            //在参数中添加 onceToken
//            paramMaps.put("onceToken",application.getOnceToken());
            //String s = application.getOnceToken();
            //Logger.e(s);
            DataUtil.requestDateContrl(paramMaps, request);
            // 添加到请求队列
            CallServer.getRequestInstance().add(context, what, request, httpListener, true, false);
        }
        paramMaps.clear();
    }



    /**
     * post 请求
     */
    @Override
    public void post(){
        Map<Integer,Class<?>> typeClassMaps=new HashMap<>();
        typeClassMaps.put(type,clazz);

        whatMapMaps.put(what,typeClassMaps);
//        if(!isNetworkAvailable((Activity) context)){
//            if(sbListener != null){
//                sbListener.httpFailed(what,Constant.WRONG_TYPE_1,jsonResult);
//            }
//            return;
//        }
        request = NoHttp.createStringRequest(SysConfig.SERVER_HOST_ADDRESS + url, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        if (request != null) {
            //在参数中添加 onceToken
//            paramMaps.put("onceToken",application.getOnceToken());
            DataUtil.requestDateContrl(paramMaps, request);
            // 添加到请求队列
            CallServer.getRequestInstance().add(context, what, request, httpListener, true, false);
        }
        paramMaps.clear();
    }

    @Override
    public void release() {
        if(sbListener != null){
            sbListener = null;
        }
        if(request != null){
            if (!request.isFinished()){
                request.cancel();
            }
        }
    }


    /**
     * 请求监听
     */
    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) throws JSONException {

                int responseCode = response.getHeaders().getResponseCode();// 服务器响应码
                if (responseCode == 200) {
                    if (RequestMethod.HEAD != response.getRequestMethod()) {
                        try {
                            jsonResult = new JSONObject(response.get());
//                            Logger.json("sjson",jsonResult.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            //   1.请求成功
                            if (jsonResult != null && jsonResult.getString("code").equals(SysConfig.ERROR_CODE_SUCCESS)) {
                                //将最新的 onceToken 设置
//                                if (!DataUtil.isOnceTokenEmpty(jsonResult.getString("onceToken"))) {
//                                    application.setOnceToken(jsonResult.getString("onceToken"));
//                                }
                                //sbListener回调不为空
                                if(sbListener != null){
                                    Object object=new Object();
                                    List list=new ArrayList();

                                    //根据不同类型，进行解析
                                    Map<Integer,Class<?>> typeClassMaps;

                                    //判断  whatMapMaps 是否含有当前 what
                                    if(whatMapMaps.containsKey(what)){
                                        typeClassMaps=whatMapMaps.get(what);

                                        if(typeClassMaps.containsKey(JSON_TO_BEAN)){
                                            //转换成 bean
                                            object= GsonControl.getPerson(jsonResult.getString("data"),
                                                    typeClassMaps.get(JSON_TO_BEAN));
                                        }else if(typeClassMaps.containsKey(JSON_TO_LIST)){
                                            //转换成 list
                                            list= JsonControl.getObjectsFromJson(jsonResult.getString("data"),
                                                    typeClassMaps.get(JSON_TO_LIST));
                                        }

                                    }
                                    //将请求的结果 传递到 外面
                                    sbListener.httpSuccess(what,object,list,jsonResult);
                                }else {
                                    Exception exception=new Exception("请传入实例化的 SBHttpListener");
                                    exception.printStackTrace();
                                }
//                                Log.i("", "---------------------SBAskHttp------------------------");
//                                Log.i("SBAskHttp", "onSucceed: 请求成功"+what);
                            } else {// 2.请求成功，返回不正确code
                                //           do : showToast(jsonResult.getString("info"));
//                                Log.i("", "---------------------SBAskHttp------------------------");
//                                Log.i("SBAskHttp", "onSucceed: 请求成功，但服务器返回的不是000000  what:"+what);
                                //将最新的 onceToken 设置
//                                if (!DataUtil.isOnceTokenEmpty(jsonResult.getString("onceToken"))) {
//                                    application.setOnceToken(jsonResult.getString("onceToken"));
//                                }
                                if(sbListener != null){
                                    sbListener.httpFailed(what,Constant.WRONG_TYPE_1,jsonResult);
                                }
                            }
                        } catch (Exception e) {    // 3.请求成功，，返回正确code，解析json异常
                            //将最新的 onceToken 设置
//                            if (!DataUtil.isOnceTokenEmpty(jsonResult.getString("onceToken"))) {
//                                application.setOnceToken(jsonResult.getString("onceToken"));
//                            }
                            e.printStackTrace();
//                            Log.i("", "---------------------SBAskHttp------------------------");
//                            Log.i("SBAskHttp", "onSucceed: error jsonResult： jsonResult异常  what:"+what);
                            if(sbListener != null){
                                sbListener.httpFailed(what, Constant.WRONG_TYPE_2,jsonResult);
                            }
                        }//********************* end try ******************
                    }

                }//4.请求失败，服务器返回 错误responseCode 404 do: 如果有刷新动作，关闭 MyToast.show(mContext, "系统异常");
                else{
//                    Log.i("", "---------------------SBAskHttp------------------------");
//                    Log.i("SBAskHttp", "onSucceed:  服务器 404  505 500 ....  what:"+what);
                    if(sbListener != null){
                        jsonResult=null;
                        sbListener.httpFailed(what,Constant.WRONG_TYPE_3,jsonResult);
                    }
                }

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) throws JSONException {
           // 5.请求失败，服务器无响应 do: 如果有刷新动作，关闭 MyToast.show(mContext, "网络错误");
//            Log.i("", "---------------------SBAskHttp------------------------");
//            Log.i("SBAskHttp", "onFailed:  服务器无响应  what:"+what);
            if(sbListener != null){
                jsonResult=null;
                sbListener.httpFailed(what,Constant.WRONG_TYPE_4,jsonResult);
            }
        }

    };

    /**
     * 网络是否连接
     */
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static class Builder{
        private Context context;
        private SBHttpListener sbListener;

        private String url="";
        private int what=-1;

        private int type=JSON_TO_NONE;
        private Class<?> clazz=null;
        //参数集合
        private Map<String, String> paramMaps = new HashMap<>();



        public Builder(){
//            this.context=context.getApplicationContext();
        }

        public Builder excute(String url, int what, SBHttpListener sbListener){
            this.sbListener=sbListener;
            this.url=url;
            this.what=what;
            return this;
        }

        /**
         * json 转换
         * @param cls
         * @param type
         * @return
         */
        public Builder jsonFromat(Class<?> cls, int type){
            this.clazz=cls;
            this.type=type;
            //防止多次请求多次异步请求，导致传进来的 Class 不匹配
            return this;
        }

        public Builder put(String key, String values){
//        paramMaps.put(key, URLEncoder.encode(values));
            paramMaps.put(key, values);
            return this;
        }

        public CommonModel build(){
            CommonModel helper= CommonModel.getInstance();
            apply(helper);

            return helper;
        }

        private void apply(CommonModel helper){
            helper=new CommonModel();
            helper.context=MyApplication.getInstance().getApplicationContext();
            helper.application= (MyApplication) MyApplication.getInstance().getApplicationContext();
            helper.sbListener=this.sbListener;
            helper.paramMaps=this.paramMaps;
            helper.what=this.what;
            helper.url=this.url;
            helper.type=this.type;
            helper.clazz=this.clazz;
        }

    }

}
