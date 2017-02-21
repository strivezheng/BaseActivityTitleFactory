package com.xunku.base.baseActFrgt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xunku.xunkubase.R;

/**
 * Created by Administrator on 2017/1/19.
 */

public class EmptyView extends LinearLayout implements View.OnClickListener {
    public static final int NETWORK_LOADING = 1; // 加载中
    public static final int NODATA = 2; // 没有数据
    public static final int NETWORK_ERROR = 3; // 网络错误
    public static final int HIDE_LAYOUT = 4; // 隐藏

    private int mErrorState = NETWORK_LOADING;//初始化为加载状态

    private ProgressBar animProgress;
    private ImageView img;
    private TextView tv;

    private String strNoDataContent;
    private String strErrorContent;
    private int imgNoDataImage = -1;
    private int imgErrorImage = -1;

    private OnClickListener listener;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_empty, null);
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);

        //初始化设置
        if (getVisibility() == View.GONE) {
            setErrorType(HIDE_LAYOUT);
        }
        else {
            setErrorType(NETWORK_LOADING);
        }
        setOnClickListener(this);
        //图片去触发点击事件
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
            }
        });
        addView(view);
    }

    //自定义点击监听（图片会拦截 EmptyVIew的点击事件，所以也需要对图片进行设置点击事件）
    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    //整个EmptyVIew去触发点击事件
    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    //判断3种状态
    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    public boolean isLoadingNoData() {
        return mErrorState == NODATA;
    }

    //传入不同状态的图片 文字
    public void setErrorImag(int imgResource) {
        imgErrorImage = imgResource;
    }

    public void setNoDataImag(int imgResource) {
        imgNoDataImage = imgResource;
    }

    public void setErrorContent(String msg) {
        strErrorContent = msg;
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setErrorType(int type) {
        setVisibility(View.VISIBLE);
        mErrorState = type;
        switch (type) {
            case NETWORK_LOADING:
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                tv.setText("正在加载...");
                setVisibility(View.VISIBLE);
                break;
            case NODATA:
                animProgress.setVisibility(View.GONE);
                img.setImageResource(imgNoDataImage == -1 ? R.drawable.ic_empty_order1 : imgNoDataImage);
                img.setVisibility(View.VISIBLE);
                tv.setText(strNoDataContent == null ? "点击屏幕，重新加载" : strNoDataContent);
                tv.setVisibility(View.VISIBLE);
                setVisibility(View.VISIBLE);
                break;
            case NETWORK_ERROR:
                animProgress.setVisibility(View.GONE);
                img.setImageResource(imgErrorImage == -1 ? R.drawable.ic_empty_order1 : imgErrorImage);
                img.setVisibility(View.VISIBLE);
                tv.setText(strErrorContent == null ? "点击屏幕，重新加载" : strErrorContent);
                tv.setVisibility(View.VISIBLE);
                setVisibility(View.VISIBLE);
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE) {
            mErrorState = HIDE_LAYOUT;
        }
        super.setVisibility(visibility);
    }
}
