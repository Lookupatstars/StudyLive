package com.aaron.studylive.widget;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.aaron.studylive.R;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe:  标签页的基类，tab标签页，首页-问答-我的课程-我的
 */
public class BasePager {

    public Activity mActivity;
    public FrameLayout fl_content;  //空的帧布局对象，需要动态的添加布局
    public View mRootView; //当前页面的布局对象

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    //初始化布局
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_pager,null);
        fl_content = view.findViewById(R.id.fl_content);
        return view;
    }

    //初始化数据
    public void initData(){

    }

}
