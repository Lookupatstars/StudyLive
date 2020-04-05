package com.aaron.studylive.widget.implement.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.aaron.studylive.widget.BaseMenuDetailPager;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe: 标签页的类
 */
public class TabDetailPager  extends BaseMenuDetailPager {
    public TabDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        //要给帧布局填充一个布局。数据
        TextView view = new TextView(mActivity); //mActivity 是来自基类的对象
        view.setText("页面列表签");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
