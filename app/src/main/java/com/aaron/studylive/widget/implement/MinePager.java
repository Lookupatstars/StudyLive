package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.studylive.AboutActivity;
import com.aaron.studylive.MineActivity;
import com.aaron.studylive.R;
import com.aaron.studylive.widget.BasePager;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 我的
 */
public class MinePager extends BasePager {
    private LinearLayout ll_about;

    public MinePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.activity_mine,null);
        ll_about = view.findViewById(R.id.ll_about);
        ll_about.setOnClickListener(new AboutOurs());
        return view;
    }

    @Override
    public void initData() {


    }

    //就好像侧边栏一样，点击更新列表，同时跳转对应的界面
    private class AboutOurs implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
