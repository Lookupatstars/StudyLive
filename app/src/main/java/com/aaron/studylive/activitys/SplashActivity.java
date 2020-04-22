package com.aaron.studylive.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.PrefUtils;

/**
 * Created by Aaron on 2020/2/25
 * The current project is StudyLive
 *
 * @Describe:  闪屏界面
 */

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rl_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_splash = findViewById(R.id.rl_splash);

        //渐变动画
        AlphaAnimation animation  = new AlphaAnimation(0,1);
        animation.setDuration(2000); //持续时间
        animation.setFillAfter(true);//保持动画结束状态

        //动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animation);

        //启动动画
        rl_splash.startAnimation(set);
        //监听动画是否结束  判断启动引导页
        set.setAnimationListener(new JudgeWhich());

    }

    //监听动画是否结束  判断启动引导页
    private class JudgeWhich implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        //动画结束后执行
        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否跳转页面
            //如果是第一次进入就要进入新手引导页，否则进入首页
            boolean isFirst = PrefUtils.getBoolen(SplashActivity.this,"is_first",true);

            Intent intent;
            if (isFirst){
                //调到新手引导
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }else {
                //调到主页面
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
