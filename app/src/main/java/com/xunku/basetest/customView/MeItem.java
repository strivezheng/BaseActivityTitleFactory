package com.xunku.basetest.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunku.basetest.R;


/**
 * Created 郑贤鑫 on 2017/2/10.
 *
 * 主要用于 app  我的 模块  里面有多个item
 * 可能还有很多不够周到的地方，以后再修改
 */

public class MeItem extends LinearLayout {

    ImageView img_left;
    TextView tv_left;
    TextView tv_right;
    ImageView img_right;

    public MeItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_me_item,this,true);

        img_left= (ImageView) findViewById(R.id.img_left);
        tv_left= (TextView) findViewById(R.id.tv_left);
        tv_right= (TextView) findViewById(R.id.tv_right);
        img_right= (ImageView) findViewById(R.id.img_right);

//        TypedArray attributes = context.obtainStyledAttributes(attrs,R.styleable.mItem);
        TypedArray attributes = context.obtainStyledAttributes(attrs,R.styleable.MeItem);

        if(attributes != null){

            //图片资源
            int leftImgRes;
            int rightImgRes;

            //文字资源
            String leftText;
            String rightText;

            //大小
            int leftImgSize;
            int leftTextSize;
            int rightImgSize;
            int rightTextSize;

            //颜色
            int leftTextColor;
            int rightTextColor;

            leftImgRes=attributes.getResourceId(R.styleable.MeItem_leftImg,-1);
            rightImgRes=attributes.getResourceId(R.styleable.MeItem_rightImg,-1);

            leftText=attributes.getString(R.styleable.MeItem_leftText);
            rightText=attributes.getString(R.styleable.MeItem_rightText);

            leftImgSize = attributes.getDimensionPixelSize(R.styleable.MeItem_leftImgSize,40);
            leftTextSize = attributes.getDimensionPixelSize(R.styleable.MeItem_leftTextSize,16);
            rightImgSize = attributes.getDimensionPixelSize(R.styleable.MeItem_rightImgSize,40);
            rightTextSize = attributes.getDimensionPixelSize(R.styleable.MeItem_rightTextSize,16);

            leftTextColor=attributes.getColor(R.styleable.MeItem_leftTextColor, Color.BLACK);
            rightTextColor=attributes.getColor(R.styleable.MeItem_rightTextColor, Color.BLACK);



//            img_left.setImageResource(rightImgRes);
//            tv_left.setText(leftText);
//            tv_right.setText(rightText);

            //左边图片
            setImg(img_left,leftImgRes,leftImgSize);
            //右边图片
            setImg(img_right,rightImgRes,rightImgSize);

            //左边文字
            setTextView(tv_left,leftText,leftTextSize,leftTextColor);
            //右边文字
            setTextView(tv_right,rightText,rightTextSize,rightTextColor);

        }
        attributes.recycle();
    }

    /**
     * 图片设置
     * @param img
     * @param res
     * @param size
     */
    private void setImg(ImageView img,int res,int size){
//        Log.i(img.getClass().getSimpleName(), "setImg: "+size);


        ViewGroup.LayoutParams lp=img.getLayoutParams();

        if(-1 == res){
            lp.height=0;
            lp.width=0;
            img.setVisibility(GONE);
        }else {
            lp.height=size;
            lp.width=size;
            img.setImageResource(res);
            img.setVisibility(VISIBLE);
        }

        img.setLayoutParams(lp);
    }

    /**
     * 文字的设置
     * @param tv
     * @param text
     * @param size
     * @param color
     */
    private void setTextView(TextView tv,String text,int size,int color){
//        Log.i(tv.getClass().getSimpleName(), "setTextView: "+text+"  "+size+"   "+color);
        tv.setText(text);
        tv.setTextSize(size);
        tv.setTextColor(color);
    }


    /**
     * 用于修改 右侧文字，
     * @param text
     */
    public void setRightText(String text){
        tv_right.setText(text);
    }
}
