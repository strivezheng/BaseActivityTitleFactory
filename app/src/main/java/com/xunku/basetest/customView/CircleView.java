package com.xunku.basetest.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xunku.basetest.R;

/**
 * Created 郑贤鑫 on 2017/2/13.
 */

public class CircleView extends View{

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;

    /**
     * 圈的宽度
     */
    private int mCircleWidth;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    public CircleView(Context context) {
//        super(context);
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        int n = a.getIndexCount();

        //得到各种属性
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CircleView_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircleView_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CircleView_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleView_speed:
                    mSpeed = a.getInt(attr, 20);// 默认20
                    break;
            }
        }
        //回收资源
        a.recycle();

        mPaint = new Paint();

        //绘图线程
        new Thread(){
            @Override
            public void run() {
//                super.run();
                while (true){
                    //进度
                    mProgress++;
                    if(mProgress == 360){
                        mProgress = 0;
                        if(!isNext){
                            isNext=true;
                        }else {
                            isNext=false;
                        }
                    }
                    //Invalidate()不能直接在线程中调用
                    //postInvalidate()可以直接在工作线程中刷新view
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int center = getWidth() / 2;            //获取圆心的 X 左边
        int radius = center-mCircleWidth / 2;   //半径
        mPaint.setStrokeWidth(mCircleWidth);    //环形的宽度
        mPaint.setAntiAlias(true);              //抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);    //设置样式  空心

        // Rect和RecF的用法基本类似 , Rect的参数为int类型，而RectF的参数类型为float类型
        // 用于定义的圆弧的形状和大小的界限
        RectF oval=new RectF(center - radius, center - radius, center + radius, center + radius);

        if(!isNext){
            // 第一颜色的圈完整，第二颜色跑
            //设置颜色
            mPaint.setColor(mFirstColor);                   //设置颜色
            canvas.drawCircle(center,center,radius,mPaint); //画出圆环

            mPaint.setColor(mSecondColor);                  //设置颜色

            //oval :指定圆弧的外轮廓矩形区域。
            //startAngle: 圆弧起始角度，单位为度。
            //sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
            //useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。关键是这个变量，
            canvas.drawArc(oval,-90,mProgress,false,mPaint);//根据进度画圆弧

        }else
        {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环

            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }

    }
}
