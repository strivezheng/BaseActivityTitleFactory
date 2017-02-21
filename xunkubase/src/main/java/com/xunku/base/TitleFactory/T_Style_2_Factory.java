package com.xunku.base.TitleFactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunku.base.TitleFactory.interfaces.StyleCallBack;
import com.xunku.base.TitleFactory.interfaces.Style_2_Callback;
import com.xunku.xunkubase.R;

/**
 * Created 郑贤鑫 on 2017/2/8.
 */

public class T_Style_2_Factory extends T_Factoryable {

    // 样式 2     +--------------------------------+
    //            | < 返回       标题       文字★ |
    //            +--------------------------------+

    //            +--------------------------------+
    //            | < 返回       标题           ★ |
    //            +--------------------------------+

    //            +--------------------------------+
    //            | <            标题           ★ |
    //            +--------------------------------+

    private Context context;

    //左边文字
    private String leftString="";
    //中间标题
    private String centerString="";
    //右边文字
    private String rightString="";

    //左边图片资源
    private int leftImgRes;
    //右边图片资源
    private int rightImgRes;


    //点击事件的回调
    private StyleCallBack styleCallback;


    Style_2_Callback style_2_Callback;




    @Override
    public View getTitleView() {
        RelativeLayout view;

        //得到布局
        view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.topbar_style1, null);
//        RelativeLayout.LayoutParams emptyRootLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        view.setLayoutParams(emptyRootLP);
        //左边文字
        TextView tv_left= (TextView) view.findViewById(R.id.tv_left2);
        //中间标题
        TextView tv_center= (TextView) view.findViewById(R.id.tv_center);
        //右边文字
        TextView tv_right1= (TextView) view.findViewById(R.id.tv_right1);
        //左边图标  一般情况下不需要动
        ImageView img_left1= (ImageView) view.findViewById(R.id.img_left1);
        //右边图标
        ImageView img_right2= (ImageView) view.findViewById(R.id.img_right2);

        //textView赋值
        tv_left.setText(leftString);
        tv_center.setText(centerString);
        tv_right1.setText(rightString);

        //在没赋值的情况下 左边、右边图标不变
        if(-1 != leftImgRes){
            img_left1.setImageResource(leftImgRes);
        }
        if(-1 != rightImgRes){
            img_left1.setImageResource(rightImgRes);
        }


        //策略模式
        style_2_Callback= (Style_2_Callback) styleCallback;

        //设置点击事件
        img_left1.setOnClickListener(leftClickListener);
        tv_left.setOnClickListener(leftClickListener);

        img_right2.setOnClickListener(rightClickListener);
        tv_right1.setOnClickListener(rightClickListener);

        return view;
    }

    //不同接口对应的不同事件
    View.OnClickListener leftClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(style_2_Callback != null){
                    style_2_Callback.leftClick();
            }


        }
    };
    View.OnClickListener rightClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(style_2_Callback != null){
                style_2_Callback.rightClick();
            }
        }
    };



    /**
     * Build 模式，负责ConstantView的构建
     * 使类的 构建 与 使用 分离，能让你 暴露出的接口使用起来更加方便清晰
     */
    public static class Builder{
        //左边文字
        private String leftString="";
        //中间标题
        private String centerString="";
        //右边文字
        private String rightString="";

        //左边图片资源
        private int leftImgRes=-1;
        //右边图片资源
        private int rightImgRes=-1;

        //上下文
        private Context context;
        //回调
        private StyleCallBack styleCallBack;



        private Builder(){}

        public Builder(Context context){
            this.context=context;
        }

        public Builder setLeftString(String leftString){
            this.leftString=leftString;
            return this;
        }
        public Builder setCenterString(String centerString){
            this.centerString=centerString;
            return this;
        }
        public Builder setRightString(String rightString){
            this.rightString=rightString;
            return this;
        }
        public Builder setLeftImgRes(int leftImgRes){
            this.leftImgRes=leftImgRes;
            return this;
        }
        public Builder setRightImgRes(int rightImgRes){
            this.rightImgRes=rightImgRes;
            return this;
        }


        public Builder setCallBack(StyleCallBack styleCallBack){
            this.styleCallBack=styleCallBack;
            return this;
        }


        /**
         * 完成 build
         * @return
         */
        public T_Style_2_Factory build(){
            T_Style_2_Factory titleFactory=new T_Style_2_Factory();

            apply(titleFactory);

            return titleFactory;
        }

        /**
         * 将build中的值赋值到  constantView类中
         * @param titleFactory
         */
        private void apply(T_Style_2_Factory titleFactory){
            titleFactory.leftString=this.leftString;
            titleFactory.rightString=this.rightString;
            titleFactory.centerString=this.centerString;

            titleFactory.leftImgRes=this.leftImgRes;
            titleFactory.rightImgRes=this.rightImgRes;

            //防止内存泄漏
            titleFactory.context=this.context.getApplicationContext();

            titleFactory.styleCallback=this.styleCallBack;
        }

    }

}
