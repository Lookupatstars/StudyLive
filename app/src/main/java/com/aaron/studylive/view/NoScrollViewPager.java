package com.aaron.studylive.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Aaron on 2020/3/30
 * The current project is StudyLive
 *
 * @Describe: 不允许滑动 ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //重写此方法，触摸时什么都不做，从而实现对滑动事件的禁用
        return true;
    }
}
