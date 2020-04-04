package com.aaron.studylive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aaron.studylive.fragment.ContentFragment;
import com.aaron.studylive.fragment.TabFragment;
import com.aaron.studylive.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aaron on 2020/2/24
 * The current project is StudyLive
 *
 * @Describe: 主页面 空布局
 */

public class MainActivityWithTab extends AppCompatActivity {
    private static final String TAG_CONTENT = "TAG_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        //替换帧布局
        initFragment();

    }

    //替换帧布局
    //初始化fragment
    private void initFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction(); //开始事务

        transaction.replace(R.id.fl_main,new ContentFragment(),TAG_CONTENT);//用fragment替换帧布局
        transaction.commit(); // 提交事务

//        Fragment fragment = fm.findFragmentByTag(TAG_CONTENT);//根据标记查找对应的fragment；

    }



}
