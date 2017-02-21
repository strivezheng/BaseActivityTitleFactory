package com.xunku.base.TitleFactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunku.base.TitleFactory.interfaces.StyleCallBack;
import com.xunku.base.TitleFactory.interfaces.Style_3_Callback;
import com.xunku.xunkubase.R;

/**
 * Created 郑贤鑫 on 2017/2/8.
 */

public class T_Style_3_Factory  extends T_Factoryable{

    // 样式 3     +--------------------------------+
    //            | <       （tab1 | tab2）        |
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

    //tab1 文字
    private String tab1Str;
    //tab2 文字
    private String tab2Str;

    Style_3_Callback style_3_Callback;



    @Override
    public View getTitleView() {

        RelativeLayout view;

        //得到布局
        view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.topbar_style3, null);
        //左边文字
//        TextView tv_left= (TextView) view.findViewById(R.id.tv_left2);
        //tab 1 文字
        final TextView tv_tab1= (TextView) view.findViewById(R.id.tv_tab1);
        //tab 2 文字
        final TextView tv_tab2= (TextView) view.findViewById(R.id.tv_tab2);
        //左边图标  一般情况下不需要动
        ImageView img_left1= (ImageView) view.findViewById(R.id.img_left1);

        //textView赋值
//        tv_left.setText(leftString);
//        tv_center.setText(centerString);
//        tv_right1.setText(rightString);

        //在没赋值的情况下 左边、右边图标不变
        if(-1 != leftImgRes){
            img_left1.setImageResource(leftImgRes);
        }
        if(-1 != rightImgRes){
            img_left1.setImageResource(rightImgRes);
        }

        //策略模式
        style_3_Callback= (Style_3_Callback) styleCallback;

        tv_tab1.setText(tab1Str);
        tv_tab2.setText(tab2Str);

        //设置点击事件
        img_left1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style_3_Callback != null){
                    style_3_Callback.leftClick();
                }
            }
        });
        tv_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style_3_Callback != null){
                    style_3_Callback.tab1Click();

                    tv_tab1.setSelected(true);//选择充值
                    tv_tab2.setSelected(false);//不选中充值卡充值
                    tv_tab1.setTextColor(context.getResources().getColor(R.color.white));
                    tv_tab2.setTextColor(context.getResources().getColor(R.color.style_3_color));
                    tv_tab1.setBackground(context.getResources().getDrawable(R.drawable.shape_style3_left));
                    tv_tab2.setBackground(context.getResources().getDrawable(R.drawable.shape_style3_right));
                }
            }
        });
        tv_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style_3_Callback != null){
                    style_3_Callback.tab2Click();

                    tv_tab1.setSelected(false);//选择充值
                    tv_tab2.setSelected(true);//不选中充值卡充值
                    tv_tab1.setTextColor(context.getResources().getColor(R.color.style_3_color));
                    tv_tab2.setTextColor(context.getResources().getColor(R.color.white));
                    tv_tab1.setBackground(context.getResources().getDrawable(R.drawable.shape_style3_right1));
                    tv_tab2.setBackground(context.getResources().getDrawable(R.drawable.shape_style3_left1));
                }
            }
        });

        return view;
    }


    /**
     * Build 模式，负责ConstantView的构建
     * 使类的 构建 与 使用 分离，能让你 暴露出的接口使用起来更加方便清晰
     */
    public static class Builder{
        //左边文字
        private String leftString="";

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

        //tab1 文字
        private String tab1Str;
        //tab2 文字
        private String tab2Str;


        private Builder(){}

        public Builder(Context context){
            this.context=context;
        }

        public Builder setLeftString(String leftString){
            this.leftString=leftString;
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
        public Builder setTab1Str(String tab1Str){
            this.tab1Str=tab1Str;
            return this;
        }

        public Builder setTab2Str(String tab2Str){
            this.tab2Str=tab2Str;
            return this;
        }


        /**
         * 完成 build
         * @return
         */
        public T_Style_3_Factory build(){
            T_Style_3_Factory titleFactory=new T_Style_3_Factory();

            apply(titleFactory);

            return titleFactory;
        }

        /**
         * 将build中的值赋值到  constantView类中
         * @param titleFactory
         */
        private void apply(T_Style_3_Factory titleFactory){
            titleFactory.leftString=this.leftString;
            titleFactory.rightString=this.rightString;

            titleFactory.leftImgRes=this.leftImgRes;
            titleFactory.rightImgRes=this.rightImgRes;


            //防止内存泄漏
            titleFactory.context=this.context.getApplicationContext();

            titleFactory.styleCallback=this.styleCallBack;

            titleFactory.tab1Str=this.tab1Str;
            titleFactory.tab2Str=this.tab2Str;
        }
    }
}
