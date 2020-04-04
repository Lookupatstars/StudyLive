package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.constant.AppContants;
import com.aaron.studylive.utils.CacheUtils;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.widget.BasePager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 首页父类的实现
 */


public class HomePager extends BasePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //要给帧布局填充一个布局。数据
        TextView view = new TextView(mActivity); //mActivity 是来自基类的对象
        view.setText("首页");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        //用基类的fl_content添加布局
        fl_content.addView(view);


    }

}
