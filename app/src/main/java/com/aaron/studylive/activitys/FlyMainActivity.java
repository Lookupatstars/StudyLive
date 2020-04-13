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

    //声明底部标签栏和界面
    private ViewPager mViewPager;
    private RelativeLayout mTabHome;
    private RelativeLayout mTabClass;
    private RelativeLayout mTabCourse;
    private RelativeLayout mTabMine;
    private TextView mTvHome;
    private TextView mTvClass;
    private TextView mTVCourse;
    private TextView mTvMine;
    //声明四个主要界面和界面列表
    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private ClassFragment mClassFragment;
    private CourseFragment mCourseFragment;
    private MineFragment mMineFragment;

    private Drawable drawable;// 给底部标签栏设置图片
    private static int[] mainMenuList = { //底部标签栏图片
            R.drawable.home648a8a8a,R.drawable.discuss648a8a8a,
            R.drawable.course648a8a8a, R.drawable.mine648a8a8a
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_main);

        //初始化布局
        initLayoutView();
        //设置显示内容
        setupContent();
        //设置底部Tab的点击事件
        setupTabClick();

    }

    //初始化布局
    private void initLayoutView() {
        //初始化主界面的内容界面和底部Tab
        mViewPager = findViewById(R.id.vp_fly_content);
        mTabHome = findViewById(R.id.tab_fly_home);
        mTabClass = findViewById(R.id.tab_fly_class);
        mTabCourse = findViewById(R.id.tab_fly_course);
        mTabMine = findViewById(R.id.tab_fly_mine);
        //底部Tab的TextView的初始化
        mTvHome = findViewById(R.id.tv_fly_home);
        mTvClass = findViewById(R.id.tv_fly_class);
        mTVCourse = findViewById(R.id.tv_fly_course);
        mTvMine = findViewById(R.id.tv_fly_mine);

    }

    //设置主界面的显示内容
    private void setupContent() {
        //初始化四个Fragment
        initFragments();

        //四个Fragment的获取
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
        mViewPager.addOnPageChangeListener(mOnPageChangeListener); //界面监听
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }
    //初始化四个Fragment
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

    //界面监听，当界面改变的时候，进行设置
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //界面被选中
        @Override
        public void onPageSelected(int position) {
            clearTabBackgroundWithTextColor();//每次点击之前，清除上次的点击

            switch (position) {
                case 0:
                    //设置DrawableTop 、DrawableLeft、DrawableRight、DrawableBottom图片
                    setDrawableTopImage(R.drawable.home641296db,mTvHome);
                    break;
                case 1:
                    setDrawableTopImage(R.drawable.discuss641296db,mTvClass);
                    break;
                case 2:
                    setDrawableTopImage(R.drawable.course641296db,mTVCourse);
                    break;
                case 3:
                    setDrawableTopImage(R.drawable.mine641296db,mTvMine);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //设置DrawableTop 、DrawableLeft、DrawableRight、DrawableBottom图片
    private void setDrawableTopImage(int id,TextView tv){
        drawable = getResources().getDrawable(id);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        tv.setTextColor(getResources().getColor(R.color.tab_text_selected));
    }

    //每次点击之前，清除上次的点击
    private void clearTabBackgroundWithTextColor() {
        ClearImageAndColor(mTvHome,0);
        ClearImageAndColor(mTvClass,1);
        ClearImageAndColor(mTVCourse,2);
        ClearImageAndColor(mTvMine,3);
    }

    //每次点击之前，清除上次的点击
    private void ClearImageAndColor(TextView tv, int position){
        drawable = getResources().getDrawable(mainMenuList[position]);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        //清除字体颜色
        tv.setTextColor(getResources().getColor(R.color.tab_text_normal));
    }

    //Tab的每次点击事件
    private void setupTabClick() {
        mTabHome.setOnClickListener(this);
        mTabClass.setOnClickListener(this);
        mTabCourse.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }

    //当点击到对应的Tab的时候，显示对应的界面
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
