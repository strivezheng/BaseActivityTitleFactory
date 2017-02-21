package com.xunku.basetest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyActivity extends AppCompatActivity {
    protected Context mContext;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_my);
        mContext=this;
        LayoutInflater inflater=LayoutInflater.from(this);

        relativeLayout= (RelativeLayout) inflater.inflate(R.layout.common_progressbar,null);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        relativeLayout.setLayoutParams(layoutParams);

    }

    /**
     * 开启进度条
     * @param context
     */
    void startViewFresh(Activity context){
        final ViewGroup rootView;
        rootView= (ViewGroup) ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        rootView.addView(relativeLayout);
    }

    /**
     * 停止进度条
     * @param context
     */
    void stopViewFresh(Activity context){
        final ViewGroup rootView;
        rootView= (ViewGroup) ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        rootView.removeView(relativeLayout);
    }

    /**
     * 延迟停止进度条
     * @param context
     * @param time
     */
    void stopViewFresh(Activity context,int time){
        final ViewGroup rootView;
        rootView= (ViewGroup) ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rootView.removeView(relativeLayout);
            }
        }, time);

    }
}
