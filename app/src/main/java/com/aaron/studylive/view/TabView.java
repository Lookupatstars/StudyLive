package com.aaron.studylive.view;

import android.animation.IntEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.studylive.R;

public class TabView extends FrameLayout {

    private ImageView mIvIcon;
    private ImageView mIvIconSelected;
    private TextView mTvTitle;

    private static final int COLOR_DEFAULT = Color.parseColor("#8a8a8a");
    private static final int COLOR_SELECTED = Color.parseColor("#1296db");



    public TabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //把写的布局加入到了tabview中
        inflate(context, R.layout.tab_view,this);

        mIvIcon = findViewById(R.id.iv_icon);
        mIvIconSelected = findViewById(R.id.iv_icon_selected);
        mTvTitle = findViewById(R.id.tv_title);

        setProgress(0);
    }

    //设置icon和text
    //1.抽取自定义属性，通过xml设置；   2.直接对外开放方法设置；
    public void setIconAndText(int icon,int iconSelected,String text){
        mIvIcon.setImageResource(icon);
        mIvIconSelected.setImageResource(iconSelected);
        mTvTitle.setText(text);

    }

    //0->1的值
    public void setProgress(float progress){
        //视图转化
        mIvIcon.setAlpha(1-progress);
        mIvIconSelected.setAlpha(progress);
        mTvTitle.setTextColor(evaluate(progress,COLOR_DEFAULT,COLOR_SELECTED));
    }


    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }

}
