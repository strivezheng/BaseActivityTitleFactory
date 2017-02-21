package com.xunku.basetest;

import android.util.Log;

import com.xunku.basetest.mvp.BaseModel;
import com.xunku.basetest.mvp.BasePresent;
import com.xunku.basetest.mvp.BaseView;
import com.xunku.basetest.mvp.CommonModel;
import com.xunku.basetest.ptrHelper.ptrConfig;
import com.xunku.basetest.view.testView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created 郑贤鑫 on 2017/2/9.
 */

public class myPresent extends BasePresent implements ptrConfig {

    private testView tView;

    public myPresent(BaseView activityView) {
        super(activityView);
        tView= (testView) activityView;
        Log.i("sinstar", "myPresent: ");
    }


    public void begin(){
        BaseModel model = new CommonModel.Builder()
                .excute("1",1,this)
                .put("id","id")
                .put("id","id")
                .put("id","id")
                .build();

        model.get();
        addModel(model);

        Log.i("sinstar", "begin: ");
        tView.initPtr(12);
        tView.initDialog("成功弹出消息");
    }

    @Override
    public void httpSuccess(int what, Object object, List list, JSONObject jsonResult) throws JSONException {

    }

    @Override
    public void httpFailed(int what, int wrong, JSONObject jsonResult) throws JSONException {

    }

    @Override
    public boolean hasMore() {
        return false;
    }

    @Override
    public void reFresh() {
        //请求网络 刷新
    }

    @Override
    public void loadMore() {
        //请求网络 加载更多
    }

//    @Override
//    public void httpSuccess(int what, Object object, List list, JSONObject jsonResult) throws JSONException {
//        switch (what){
//            case 1:
//                tView.initPtr(12);
//                tView.initDialog("成功弹出消息");
//
//                break;
//            default:
//                break;
//        }
//
//    }
//
//    @Override
//    public void httpFailed(int what, int wrong, JSONObject jsonResult) throws JSONException {
//
//    }


}
