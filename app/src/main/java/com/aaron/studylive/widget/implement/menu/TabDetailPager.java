package com.aaron.studylive.widget.implement.menu;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.Utils;
import com.aaron.studylive.widget.BannerPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe: Class menu detail  标签页的类
 *
 * bannerPager一样
 *   pager_class_menu_detail.xml
 */

public class TabDetailPager extends RelativeLayout implements View.OnClickListener{
    private static final String TAG = "BannerPager";
    private Context mContext; // 声明一个上下文对象
    private ViewPager vp_tabmenu; // 声明一个翻页视图对象
    private List<TextView> mViewList = new ArrayList<TextView>(); //声明一个TextView的视图
    private int mInterval = 2000; // 轮播的时间间隔，单位毫秒

    public TabDetailPager(Context context) {
        this(context, null);
    }

    public TabDetailPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    // 开始广告轮播
    public void start() {
        // 延迟若干秒后启动滚动任务
        mHandler.postDelayed(mScroll, mInterval);
    }

    // 停止广告轮播
    public void stop() {
        // 移除滚动任务
        mHandler.removeCallbacks(mScroll);
    }

    // 设置广告轮播的时间间隔
    public void setInterval(int interval) {
        mInterval = interval;
    }

    private Handler mHandler = new Handler(); // 声明一个处理器对象
    // 定义一个滚动任务
    private Runnable mScroll = new Runnable() {
        @Override
        public void run() {
            scrollToNext(); // 滚动广告图片
            // 延迟若干秒后继续启动滚动任务
            mHandler.postDelayed(this, mInterval);
        }
    };

    // 滚动到下一张广告图
    public void scrollToNext() {
        // 获得下一张广告图的位置
        int index = vp_tabmenu.getCurrentItem() + 1;
        if (index >= mViewList.size()) {
            index = 0;
        }
        // 设置翻页视图显示指定位置的页面
        vp_tabmenu.setCurrentItem(index);
    }

    // 设置广告图片队列
    public void setTextView(ArrayList<String> tabMenuList) {
        int dip_15 = Utils.dip2px(mContext, 15);
        // 根据图片队列生成图像视图队列
        for (int i = 0; i < tabMenuList.size(); i++) {
            String tabMenu = tabMenuList.get(i);
            TextView tv = new TextView(mContext);
            tv.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            tv.setTextColor(Color.RED);
            tv.setTextSize(22);
            tv.setText(tabMenu);
            tv.setOnClickListener(this);
            mViewList.add(tv);
        }
        // 设置翻页视图的图像翻页适配器
        vp_tabmenu.setAdapter(new TabMenuAdapater());
        // 给翻页视图添加简单的页面变更监听器，此时只需重写onPageSelected方法
        vp_tabmenu.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 高亮显示该位置的指示按钮
                setButton(position);
            }
        });
        // 设置翻页视图默认显示第一页
        vp_tabmenu.setCurrentItem(0);
        // 默认高亮显示第一个指示按钮
        setButton(0);
    }

    // 初始化视图
    private void initView() {
        // 根据布局文件banner_pager.xml生成视图对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_class_menu_detail, null);
        // 从布局文件中获取名叫vp_banner的翻页视图
//        vp_tabmenu = view.findViewById(R.id.vp_class_menu_detail);

        addView(view); // 将该布局视图添加到横幅轮播条
    }

    @Override
    public void onClick(View v) {

    }

    // 定义一个图像翻页适配器
    private class TabMenuAdapater extends PagerAdapter {

        // 获取页面项的个数
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 从容器中销毁指定位置的页面
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        // 实例化指定位置的页面，并将其添加到容器中
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    }
    // 设置选中单选组内部的哪个单选按钮
    private void setButton(int position) {
//        ((RadioButton) rg_indicator.getChildAt(position)).setChecked(true);
    }
    // 设置广告图的点击监听器
    public void setOnBannerListener(BannerPager.BannerClickListener listener) {
        mListener = listener;
    }

    // 声明一个广告图点击的监听器对象
    private BannerPager.BannerClickListener mListener;

    // 定义一个广告图片的点击监听器接口
    public interface BannerClickListener {
        void onBannerClick(int position);
    }

}

/*
public class TabDetailPager  extends BaseMenuDetailPager {
    private static final String TAG = "TabDetailPager";



    private ClassMenuData mTabData; //单个标签的数据
    private TextView view;
    public TabDetailPager(Activity activity, ClassMenuData classMenuData) {
        super(activity,null);
        mTabData = classMenuData;
        L.d("TabDetailPager 类里边的构造函数  看一下mTabData的值：："+mTabData.name);
    }

    @Override
    public View initView() {
        L.d("TabDetailPager 类里边的 initView()");
        return view;
    }

    @Override
    public void initData() {
//        view.setText(mTabData.name);
        L.d("TabDetailPager中的initData()");
    }

}
*/