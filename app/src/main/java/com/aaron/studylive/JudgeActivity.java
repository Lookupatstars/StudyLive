package com.aaron.studylive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.aaron.studylive.Fragment.SplashFragment;
import com.aaron.studylive.global.AppContants;
import com.aaron.studylive.utils.SpUtils;
import com.aaron.studylive.view.transformer.ScaleTransformer;

/**
 * Created by Aaron on 2020/2/25
 * The current project is StudyLive
 *
 * @Describe: 判断是否是第一次启动
 */

public class JudgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppContants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_judge);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
