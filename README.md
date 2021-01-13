# BaseActivityTitleFactory
TitleFactory

本项目是一个Android快速开框架，项目背景是我以前待得一家外包公司，活儿非常多，半年做了多达七八个app，为了完成快速开发的需求，所以需要将可以复用的部分提取成公共模块方便调用。

通过这个项目，起码可以熟练的运用
- 工厂模式
- 构建者模式
- 单例模式
- 适配器模式
- 接口、抽象类、回调

在我看来，可复用的模块不止功能类，也可以是页面的布局元素，比如弹窗、标题栏、按钮等元素，由于是一个公司出品的app，所以整体风格元素都差不多，所以这些元素都是可以复用的。


在这个项目里有三个模块
- app：可运行demo项目
- bannerlibrary：已适配公司需求banner的第三方依赖
- xunkubase：这个就是此项目的核心，抽取出来的可复用组件


#### xunkubase组件
xunkubase组件是抽取出来的核心模块，提供了基础各种Util类，网络请求封装包，还有我觉得最有亮点的Activity的封装类BaseActivity，请开始他的表演：

> BaseActivity

BaseActivity继承自AppCompatActivity，定义了几个抽象方法

```
    /**
     * 子类必须实现，返回一个写好了的 topbar,如果不需要标题，实现方法后返回null就行，
     * @return
     */
    abstract protected View getTopBar();

```


```

    /**
     * 点击屏幕，重新加载事件
     */
     abstract protected void reLoad();

```

以及protected方法


```

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
```


这里重点是getTopBar()方法，他要求实现类必须返回一个view，这个view就是标题栏渲染后的view，然后我定义了一个工厂类，专门用来创建各种类型的标题栏view

1. 定义工厂类接口，同时注释也显示了，已经实现了哪些样式的标题栏，需要哪种样式，返回该类型就行

```

/**
 * Created 郑贤鑫 on 2017/2/8.
 */

public abstract class T_Factoryable {

    ///////////////////////////////////////////////////////////////////////////

    // 样式 1     +--------------------------------+
    //            | < 返回        标题             |
    //            +--------------------------------+

    //            +--------------------------------+
    //            | <             标题             |
    //            +--------------------------------+
    //


    // 样式 2     +--------------------------------+
    //            | < 返回       标题       文字★ |
    //            +--------------------------------+

    //            +--------------------------------+
    //            | < 返回       标题           ★ |
    //            +--------------------------------+

    //            +--------------------------------+
    //            | <            标题           ★ |
    //            +--------------------------------+



    // 样式 3     +--------------------------------+
    //            | <       （tab1 | tab2）        |
    //            +--------------------------------+



    //
    ///////////////////////////////////////////////////////////////////////////

    abstract public View getTitleView();

}

```
当然少不了对应的标题栏点击事件的回调

抽象接口StyleCallBack
```
/**
 * Created 郑贤鑫 on 2017/1/19.
 */

public interface StyleCallBack {
}

```
具体不同类型的实现接口

```
/**
 * Created 郑贤鑫 on 2017/2/8.
 */

public interface Style_3_Callback extends StyleCallBack {
    void leftClick();
    void tab1Click();
    void tab2Click();
}

```

再后面就是具体返回的实现类，这里不具体贴代码了，大家可以去看源码。

#### 如何使用

参照demo里的MainActivity，只需要实现getTopBar()方法，返回一个view即可，这个view呢就通过工厂类生成，工厂类通过建造者模式，eg：

```
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
```

这样，一个基础的页面就完成了，当然在基类里面还封装了很多其他有用的方法，感兴趣的同学可以下载项目，看看源码。










