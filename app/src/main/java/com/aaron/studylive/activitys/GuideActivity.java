package com.aaron.studylive.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.PrefUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 第一次启动显示页  新手引导页
 * */

@SuppressWarnings("deprecation")
public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;// 小红点
    private Button btnStart;

    private ArrayList<ImageView> mImageViewList; // imageView集合

    // 引导页图片id数组
    private int[] mImageIds = new int[] {
            R.drawable.start1,
            R.drawable.start2,
            R.drawable.start3
    };

    // 小红点移动距离
    private int mPointDis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题，必须在setContentView初始化界面之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ActivityCollector.addActivity(this);

        mViewPager = findViewById(R.id.vp_guide);
        llContainer =  findViewById(R.id.ll_dot);
        ivRedPoint =  findViewById(R.id.iv_dot);
        btnStart = findViewById(R.id.btn_start);

        ivRedPoint.setImageResource(R.drawable.shape_dot_red);

        initData();// 先初始化数据
        mViewPager.setAdapter(new GuideAdapter());// 设置数据

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 某个页面被选中
                if (position == mImageViewList.size() - 1) {// 最后一个页面显示开始体验的按钮
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 当页面滑动过程中的回调
                // 更新小红点距离
                int leftMargin = (int) (mPointDis * positionOffset) + position
                        * mPointDis;// 计算小红点当前的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint
                        .getLayoutParams();
                params.leftMargin = leftMargin;// 修改左边距

                // 重新设置布局参数
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面状态发生变化的回调
            }
        });

        // 计算两个圆点的距离
        // 移动距离=第二个圆点left值 - 第一个圆点left值
        // measure->layout(确定位置)->draw(activity的onCreate方法执行结束之后才会走此流程)
        // mPointDis = llContainer.getChildAt(1).getLeft()
        // - llContainer.getChildAt(0).getLeft();
        // System.out.println("圆点距离:" + mPointDis);

        // 监听layout方法结束的事件,位置确定好之后再获取圆点间距
        // 视图树
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // 移除监听,避免重复回调
                        ivRedPoint.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        // ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        // layout方法执行结束的回调
                        mPointDis = llContainer.getChildAt(1).getLeft()
                                - llContainer.getChildAt(0).getLeft();
                        System.out.println("圆点距离:" + mPointDis);
                    }
                });

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //更新sp, 已经不是第一次进入了
                PrefUtils.setBoolen(getApplicationContext(), "is_first", false);

                //跳到主页面
                startActivity(new Intent(getApplicationContext(), LoginContentActivity.class));
                finish();
            }
        });
    }

    //初始化数据，设置图片
    // 初始化数据
    private void initData() {
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);// 通过设置背景,可以让宽高填充布局
            // view.setImageResource(resId)
            mImageViewList.add(view);

            // 初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_dot_gray);// 设置图片(shape形状)

            // 初始化布局参数, 宽高包裹内容,父控件是谁,就是谁声明的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i > 0) {
                // 从第二个点开始设置左边距
                params.leftMargin = 10;
            }

            point.setLayoutParams(params);// 设置布局参数

            llContainer.addView(point);// 给容器添加圆点
        }
    }

    class GuideAdapter extends PagerAdapter {

        // item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        // 销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
