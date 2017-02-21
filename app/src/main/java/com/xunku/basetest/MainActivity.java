package com.xunku.basetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xunku.base.TitleFactory.T_Style_3_Factory;
import com.xunku.base.TitleFactory.interfaces.Style_3_Callback;
import com.xunku.base.baseActFrgt.BaseActivity;
import com.xunku.basetest.customView.MeItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    Banner banner;

    List<String> images=new ArrayList<>();
    List<String> titles=new ArrayList<>();

    MeItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewType(HIDE_LAYOUT);

        images.add("http://tupian.enterdesk.com/2014/lxy/2014/12/03/6/8.png");
        images.add("https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/3b87e950352ac65c1b6a0042f9f2b21193138a97.jpg");
        images.add("http://image.tianjimedia.com/uploadImages/2015/111/23/A2J00V7P5VN4.JPEG");
        images.add("http://image.tianjimedia.com/uploadImages/2015/083/30/VVJ04M7P71W2.jpg");

        titles.add("123");
        titles.add("456");
        titles.add("789");
        titles.add("111");

        banner= (Banner) findViewById(R.id.banner);

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        item= (MeItem) findViewById(R.id.meItem);


    }


    @Override
    protected View getTopBar() {
        return new T_Style_3_Factory.Builder(this)
                .setTab1Str("123")
                .setTab2Str("456")
                .setCallBack(new Style_3_Callback() {
                    @Override
                    public void leftClick() {

                    }

                    @Override
                    public void tab1Click() {


                    }

                    @Override
                    public void tab2Click() {
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
//                        item.setRightText("888");
                    }
                })
                .build()
                .getTitleView();
    }

    @Override
    protected void reLoad() {

    }


}
