package com.aaron.studylive.widget;

import android.app.Activity;
import android.view.View;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe:  courseWeb 中 content的内容类的封装   --基类
 *
 */
public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public View mRootView; //跟布局

    public BaseMenuDetailPager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }
    //初始化布局
    //因为每个VIew的差距过大，所以交给子类去实现，这里创建一个抽象的方法
    public abstract View initView();

    public void initData(){

    }

}
