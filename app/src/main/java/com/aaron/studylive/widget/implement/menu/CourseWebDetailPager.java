package com.aaron.studylive.widget.implement.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.widget.BaseMenuDetailPager;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe:  课程详情页 -- 实际上不显示内容，只是一个基类
 */
public class CourseWebDetailPager extends BaseMenuDetailPager {
    private ViewPager vp_class_menu_detail;
    public CourseWebDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        //用以额新的布局 pager_class_menu_detail 替换这个
        View view = View.inflate(mActivity, R.layout.pager_class_menu_detail,null);
        vp_class_menu_detail = view.findViewById(R.id.vp_class_menu_detail);
        return view;
    }

    class ClassMenuDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object );
        }
    }

}
