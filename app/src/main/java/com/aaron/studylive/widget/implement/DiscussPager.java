package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.aaron.studylive.widget.BasePager;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 问答
 */
public class DiscussPager extends BasePager {
    public DiscussPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //要给帧布局填充一个布局。数据
        TextView view = new TextView(mActivity); //mActivity 是来自基类的对象
        view.setText("问答");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        //用基类的fl_content添加布局
        fl_content.addView(view);
    }
}
