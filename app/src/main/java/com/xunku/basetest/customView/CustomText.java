package com.xunku.basetest.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.xunku.base.utils.DataUtil;
import com.xunku.basetest.R;

/**
 * Created 郑贤鑫 on 2017/2/10.
 */

public class CustomText extends View {

    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 文本的背景颜色
     */
    private int bgColor;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public CustomText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes=context.obtainStyledAttributes(attrs, R.styleable.CustomText);

        if(attributes != null){
            mTitleText=attributes.getString(R.styleable.CustomText_text);
            mTitleTextColor=attributes.getColor(R.styleable.CustomText_textColor, Color.BLACK);
            mTitleTextSize=attributes.getDimensionPixelSize(R.styleable.CustomText_textSize,DataUtil.dip2px(context,16));
            bgColor=attributes.getColor(R.styleable.CustomText_bgColor,Color.GREEN);

        }else {
            mTitleText="";
            mTitleTextColor=Color.BLACK;
            mTitleTextSize= DataUtil.dip2px(context,16);
            bgColor=Color.WHITE;
        }

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    /**
     * 如果不重新onMeasure（）方法，当使用自定义控件的时候，如果宽高都是用的wrap_content，那么事实上
     * 并不会这样，他们会全充满屏幕
     * 鸿扬大神的原话是：http://blog.csdn.net/lmj623565791/article/details/24252901
     * 系统帮我们测量的高度和宽度都是MATCH_PARNET，当我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果，当我们设置为WRAP_CONTENT,或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
     所以，当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法”：

     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取系统的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //实际的 宽高
        int width;
        int height;


        if(widthMode == MeasureSpec.EXACTLY){//如果是 定义属性的时候给了明确的大小，或者定义为match_parent
            width=widthSize;
        }else {     //如果给的是wrap_content，那么这个控件的宽度需要根据内容的宽度来决定
            mPaint.setTextSize(mTitleTextSize);
            //// TODO: 2017/2/13 这句怎么理解
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth=mBound.width();

            //控件的宽度 等于：两边的padding+文字的宽度；
            int desired= (int) (getPaddingLeft()+textWidth+getPaddingRight());

            width=desired;
        }

        if(heightMode == MeasureSpec.EXACTLY){//如果是 定义属性的时候给了明确的大小，或者定义为match_parent
            height=heightSize;
        }else {     //如果给的是wrap_content，那么这个控件的宽度需要根据内容的宽度来决定
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeight=mBound.height();

            //控件的宽度 等于：两边的padding+文字的宽度；
            int desired= (int) (getPaddingTop()+textHeight+getPaddingBottom());

            height=desired;
        }

        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        mPaint.setColor(bgColor);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2 - mBound.width()/2,getHeight()/2 - mBound.width()/2,mPaint);
//        canvas.drawText(mTitleText,0,0,mPaint);

    }

//    @Override
//    public void layout(int l, int t, int r, int b) {
//        super.layout(l, t, r, b);
//
//    }
}
