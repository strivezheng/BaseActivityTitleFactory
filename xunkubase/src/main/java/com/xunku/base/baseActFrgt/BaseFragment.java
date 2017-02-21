package com.xunku.base.baseActFrgt;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.xunku.base.common.MyToast;
import com.xunku.xunkubase.R;


/**
 *  Created by 郑贤鑫  16-12-2
 */
public abstract class BaseFragment extends Fragment {

    /**
     *  加载中
     */
    public static final int NETWORK_LOADING = 1;

    /**
     * 没有数据
     */
    public static final int NO_DATA = 2;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 3;

    /**
     * 隐藏 加载 或者 无数据 或者 网络错误 等布局，显示正常的内容
     */
    public static final int HIDE_LAYOUT = 4;



    private FrameLayout flBaseTop;//头布局
    private FrameLayout flBaseContent;//内容布局

    private RelativeLayout flBaseStatus;//状态布局
    private EmptyView evBaseStatus;//状态空间

    public Activity mActivity;

    /** fragment inflate 的布局**/
    public View layoutview;

    /**
     * 用来包裹fragment的布局
     */
    View RootWrapper;


    public BaseFragment() {
        // Required empty public constructor
    }

    // Fragment被创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();// 获取所在的activity对象
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //根部局
        RootWrapper=LayoutInflater.from(mActivity).inflate(R.layout.activity_base,null);

        //子类的布局
        layoutview = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);

        flBaseTop = (FrameLayout)RootWrapper.findViewById(R.id.fl_base_top);
        flBaseContent = (FrameLayout) RootWrapper.findViewById(R.id.fl_base_content);

        flBaseStatus = (RelativeLayout) RootWrapper.findViewById(R.id.fl_base_status);
        evBaseStatus = (EmptyView) RootWrapper.findViewById(R.id.ev_base_status);


        //点击屏幕，重新加载
        evBaseStatus.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evBaseStatus.setErrorType(EmptyView.NETWORK_LOADING);//加载中
                flBaseContent.setVisibility(View.GONE);
                flBaseStatus.setVisibility(View.VISIBLE);

                reLoad();
            }
        });

        //内容添加内容
        flBaseContent.addView(layoutview);

        //标题添加标题
        if(getTopBar() != null){
            flBaseTop.addView(getTopBar());
            flBaseTop.setVisibility(View.VISIBLE);
        }else {
            flBaseTop.setVisibility(View.GONE);
        }


        return RootWrapper;
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 子类必须实现，如果需要标题，就返回一个构建好了的标题，如果不需要，返回 null，则标题部分不占空间
     * @return
     */
    abstract protected View getTopBar();


    /**
     * 该抽象方法就是 初始化view
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);


    /**
     * 点击屏幕，重新加载事件
     */
    protected void reLoad(){
    }

    /**
     * 设置 加载布局的状态
     * NETWORK_LOADING   ：加载中
     * NO_DATA            ：没有数据
     * NETWORK_ERROR      ：网络错误
     * HIDE_LAYOUT        :隐藏加载过程，显示正常布局
     * @param type
     */
    protected void setViewType(int type) {
        if (type == NETWORK_LOADING) {
            evBaseStatus.setErrorType(EmptyView.NETWORK_LOADING);//加载中
            flBaseContent.setVisibility(View.GONE);
            flBaseStatus.setVisibility(View.VISIBLE);
        }
        else if (type == NO_DATA) {
            evBaseStatus.setErrorType(EmptyView.NODATA);//没有数据
            flBaseContent.setVisibility(View.GONE);
            flBaseStatus.setVisibility(View.VISIBLE);
        }
        else if (type == NETWORK_ERROR) {
            evBaseStatus.setErrorType(EmptyView.NETWORK_ERROR);//网络错误
            flBaseContent.setVisibility(View.GONE);
            flBaseStatus.setVisibility(View.VISIBLE);
        }
        else if (type == HIDE_LAYOUT) {
            evBaseStatus.setErrorType(EmptyView.HIDE_LAYOUT);//隐藏
            flBaseContent.setVisibility(View.VISIBLE);
            flBaseStatus.setVisibility(View.GONE);
        }
    }

    /**
     *  设置 加载布局的状态
     * NETWORK_LOADING   ：加载中
     * NO_DATA            ：没有数据
     * NETWORK_ERROR      ：网络错误
     * HIDE_LAYOUT        :隐藏加载过程，显示正常布局
     * @param type
     * @param delayTime     延时时间   单位 ：ms
     */
    protected void setViewType(final int type, int delayTime){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewType(type);
            }
        }, delayTime);
    }



    /** 弹出吐司 **/
    protected void showToast(String msg){
        MyToast.show(getContext(),msg);
    }

}
