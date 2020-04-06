package com.aaron.studylive.widget.implement.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.aaron.studylive.bean.ClassMenu.ClassMenuData;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.widget.BaseMenuDetailPager;
import com.aaron.studylive.widget.implement.ClassPager;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe: Class menu detail  标签页的类
 */
public class TabDetailPager  extends BaseMenuDetailPager {

    private ClassMenuData mTabData; //单个标签的数据
    private TextView view;
    public TabDetailPager(Activity activity, ClassMenuData classMenuData) {
        super(activity);
        mTabData = classMenuData;
    }

    @Override
    public View initView() {
        //要给帧布局填充一个布局。数据
        view = new TextView(mActivity); //mActivity 是来自基类的对象
//        view.setText(mTabData.name); //在这里初始化会空指针
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        view.setText(mTabData.name);
        L.d("TabDetailPager中的mTabData.name:::");
    }
}
