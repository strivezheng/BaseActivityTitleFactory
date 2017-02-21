package com.xunku.base.baseActFrgt;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.xunku.base.common.MyToast;
import com.xunku.xunkubase.R;

import static android.view.ViewGroup.OnClickListener;

/**
 * Created by 邵 on 2017/1/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 基类上下文
     */
    Context mBaseContext;

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


    /**
     * 用于打印日志 会自动获取子类的类名
     */
    protected String TAG="";


    private FrameLayout flBaseTop;//头布局
    private FrameLayout flBaseContent;//内容布局

    private RelativeLayout flBaseStatus;//状态布局
    private EmptyView evBaseStatus;//状态空间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();
        mBaseContext=this;

        //去掉系统的TitleBar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //初始化content的View
        initContentView(R.layout.activity_base);

        // TODO: 2017/2/8  增加保护进程方法 
    }

    /**
     * 子类必须实现，返回一个写好了的 topbar,如果不需要标题，实现方法后返回null就行，
     * @return
     */
    abstract protected View getTopBar();

    /**
     * 以下代码可以这样理解
     * 每个Activity的窗口布局都有一个根布局
     * 那么以下的几个实例对象group
     * group : 父亲
     * activityBase：儿子
     */
    private void initContentView(@LayoutRes int layoutResID) {
        // TODO Auto-generated method stub
        ViewGroup group = (ViewGroup) findViewById(android.R.id.content);   //得到窗口的根布局
        group.removeAllViews();                                             //首先先移除在根布局上的组件
        LayoutInflater.from(this).inflate(layoutResID, group, true);        //group，true的意思表示添加上去
    }

    /**
     * 这句的意思表示将MainActivity的布局又加到parentLinearLayout的content上
     */
    @Override
    public void setContentView(@LayoutRes int layoutContentID) {
        // super.setContentView(layoutResID);//一定不能调用这句话，不然之前做的添加的布局都被覆盖了
        flBaseTop = (FrameLayout) findViewById(R.id.fl_base_top);
        flBaseContent = (FrameLayout) findViewById(R.id.fl_base_content);

        flBaseStatus = (RelativeLayout) findViewById(R.id.fl_base_status);
        evBaseStatus = (EmptyView) findViewById(R.id.ev_base_status);

        //点击屏幕，重新加载
        evBaseStatus.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                evBaseStatus.setErrorType(EmptyView.NETWORK_LOADING);//加载中
                flBaseContent.setVisibility(View.GONE);
                flBaseStatus.setVisibility(View.VISIBLE);

                reLoad();
            }
        });

        //将子类的内容布局添加到基类的内容布局中
        LayoutInflater.from(this).inflate(layoutContentID, flBaseContent, true);

        //将子类实现的标题，添加到基类的标题布局当中
        if(getTopBar() != null){
            flBaseTop.addView(getTopBar());
            flBaseTop.setVisibility(View.VISIBLE);
        }else {
            flBaseTop.setVisibility(View.GONE);
        }

        initBaseView();
    }

//    //  设置要显示的布局方法
//    public void setBaseTopView(int layoutID) {
//        //获得inflater
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //把继承该BaseAcitivyt的layoutID放进来 显示
//        View view = inflater.inflate(layoutID, null);
//        //addview
//        flBaseTop.addView(view);
//    }


    /**
     * 基类几个布局 相关初始化
     */
    private void initBaseView() {
        flBaseContent.setVisibility(View.GONE);
        flBaseStatus.setVisibility(View.VISIBLE);
        evBaseStatus.setErrorType(EmptyView.NETWORK_LOADING);//加载中
    }

    /**
     * 点击屏幕，重新加载事件
     */
     abstract protected void reLoad();

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

    /**
     * 吐司
     * @param msg
     */
    protected void showToast(String msg){
        MyToast.show(mBaseContext,msg);
    }

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

    /**
     * 隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 获取edittext的位置
     *
     * @param v
     * @param event
     * @return
     */
    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     *  在点击事件中隐藏键盘  随便传一个当前页面的 view
     * @param view
     */
    protected void hidenInput(View view){
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) mBaseContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 显示或者隐藏输入法
        //   imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_HIDDEN);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
