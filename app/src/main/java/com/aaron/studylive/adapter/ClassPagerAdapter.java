package com.aaron.studylive.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aaron.studylive.fragment.HotdisFragment;
import com.aaron.studylive.fragment.NewdisFragment;
import com.aaron.studylive.fragment.WaitdisFragment;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/8
 * The current project is StudyLive
 *
 * @Describe:
 */
public class ClassPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray; // 声明一个标题文字队列

    // 碎片页适配器的构造函数，传入碎片管理器与标题队列
    public ClassPagerAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    // 获取指定位置的碎片Fragment
    public Fragment getItem(int position) {
        if (position == 0) { // 第一页展示热门
            return new HotdisFragment();
        } else if (position == 1) { // 第二页展示最新
            return new NewdisFragment();
        } else if (position == 2){ //第三页展示等待回答
            return new WaitdisFragment();
        }
        return new HotdisFragment();
    }

    // 获取碎片Fragment的个数
    public int getCount() {
        return mTitleArray.size();
    }

    // 获得指定碎片页的标题文本
    public CharSequence getPageTitle(int position) {
        return mTitleArray.get(position);
    }
}
