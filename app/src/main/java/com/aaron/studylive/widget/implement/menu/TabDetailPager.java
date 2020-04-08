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
 *
 * bannerPager一样
 *   pager_class_menu_detail.xml
 */
public class TabDetailPager  extends BaseMenuDetailPager {

    private ClassMenuData mTabData; //单个标签的数据
    private TextView view;
    public TabDetailPager(Activity activity, ClassMenuData classMenuData) {
        super(activity);
        mTabData = classMenuData;
        L.d("TabDetailPager 类里边的构造函数  看一下mTabData的值：："+mTabData.name);
    }

    @Override
    public View initView() {
        L.d("TabDetailPager 类里边的 initView()");
        return view;
    }

    @Override
    public void initData() {
//        view.setText(mTabData.name);
        L.d("TabDetailPager中的initData()");
    }
}
