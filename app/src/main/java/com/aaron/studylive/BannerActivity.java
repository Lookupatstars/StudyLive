package com.aaron.studylive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.studylive.Fragment.SplashFragment;
import com.aaron.studylive.view.transformer.ScaleTransformer;

/**
 * Created by Aaron on 2020/2/25
 * The current project is StudyLive
 *
 * @Describe: 轮播图
 */

public class BannerActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private int[] mResIds = new int[]{
            0xffff0000,
            0xff00ff00,
            0xff0000ff,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setOffscreenPageLimit(3);
//        mVpMain.setPageMargin(20);
        mVpMain.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mResIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                //完成View的创建
                View view = new View(container.getContext());
                view.setBackgroundColor(mResIds[position]);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        mVpMain.setPageTransformer(true,new ScaleTransformer());
    }

}
