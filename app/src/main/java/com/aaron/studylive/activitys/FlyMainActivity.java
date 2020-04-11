package com.aaron.studylive.activitys;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.fragments.ClassFragment;
import com.aaron.studylive.fragments.CourseFragment;
import com.aaron.studylive.fragments.HomeFragment;
import com.aaron.studylive.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class FlyMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private RelativeLayout mTabHome;
    private RelativeLayout mTabClass;
    private RelativeLayout mTabCourse;
    private RelativeLayout mTabMine;
    private TextView mTvHome;
    private TextView mTvClass;
    private TextView mTVCourse;
    private TextView mTvMine;

    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private ClassFragment mClassFragment;
    private CourseFragment mCourseFragment;
    private MineFragment mMineFragment;

    private Drawable drawable;// 为了设值图片
    private static int[] mainMenuList = {
            R.drawable.home648a8a8a,R.drawable.discuss648a8a8a,
            R.drawable.course648a8a8a, R.drawable.mine648a8a8a
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_main);

        //初始化布局
        initLayoutView();

        setupContent();//设置显示内容
        setupTabClick();

    }

    private void initLayoutView() {
        mViewPager = findViewById(R.id.vp_fly_content);
        mTabHome = findViewById(R.id.tab_fly_home);
        mTabClass = findViewById(R.id.tab_fly_class);
        mTabCourse = findViewById(R.id.tab_fly_course);
        mTabMine = findViewById(R.id.tab_fly_mine);
        //TextView的初始化
        mTvHome = findViewById(R.id.tv_fly_home);
        mTvClass = findViewById(R.id.tv_fly_class);
        mTVCourse = findViewById(R.id.tv_fly_course);
        mTvMine = findViewById(R.id.tv_fly_mine);

    }

    //设置显示内容
    private void setupContent() {
        initFragments();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            clearTabBackgroundWithTextColor();//每次点击之前，清除上次的点击

            switch (position) {
                case 0:
                    drawable = getResources().getDrawable(R.drawable.home641296db);
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mTvHome.setCompoundDrawables(null, drawable, null, null);
                    mTvHome.setTextColor(getResources().getColor(R.color.tab_text_selected));
                    break;
                case 1:
                    drawable = getResources().getDrawable(R.drawable.discuss641296db);
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mTvClass.setCompoundDrawables(null, drawable, null, null);
                    mTvClass.setTextColor(getResources().getColor(R.color.tab_text_selected));
                    break;
                case 2:
                    drawable = getResources().getDrawable(R.drawable.course641296db);
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mTVCourse.setCompoundDrawables(null,drawable, null, null);
                    mTVCourse.setTextColor(getResources().getColor(R.color.tab_text_selected));
                    break;
                case 3:
                    drawable = getResources().getDrawable(R.drawable.mine641296db);
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mTvMine.setCompoundDrawables(null, drawable, null, null);
                    mTvMine.setTextColor(getResources().getColor(R.color.tab_text_selected));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initFragments() {
        mFragments = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mClassFragment = new ClassFragment();
        mCourseFragment = new CourseFragment();
        mMineFragment = new MineFragment();

        mFragments.add(mHomeFragment);
        mFragments.add(mClassFragment);
        mFragments.add(mCourseFragment);
        mFragments.add(mMineFragment);
    }

    //每次点击之前，清除上次的点击
    private void clearTabBackgroundWithTextColor() {
        ClearImage(mTvHome,0);
        ClearImage(mTvClass,1);
        ClearImage(mTVCourse,2);
        ClearImage(mTvMine,3);
    }

    private void ClearImage(TextView tv, int position){
        drawable = getResources().getDrawable(mainMenuList[position]);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        //清除字体颜色
        tv.setTextColor(getResources().getColor(R.color.tab_text_normal));
    }

    private void setupTabClick() {
        mTabHome.setOnClickListener(this);
        mTabClass.setOnClickListener(this);
        mTabCourse.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_fly_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_fly_class:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_fly_course:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_fly_mine:
                mViewPager.setCurrentItem(3);
                break;
        }

    }
}
