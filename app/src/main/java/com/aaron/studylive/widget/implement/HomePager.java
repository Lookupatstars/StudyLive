package com.aaron.studylive.widget.implement;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.adapter.LiveClassAdapter;
import com.aaron.studylive.bean.LiveClassInfo;
import com.aaron.studylive.constant.ImageList;
import com.aaron.studylive.utils.Utils;
import com.aaron.studylive.widget.BannerPager;
import com.aaron.studylive.widget.BasePager;
import com.aaron.studylive.widget.SpacesItemDecoration;


/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 首页父类的实现
 */

public class HomePager extends BasePager {
    private RecyclerView rv_hor_live_class;
    private ListView lv_love_class;

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.activity_banner,null);

        //初始化轮播图
        // 从布局文件中获取名叫banner_pager的横幅轮播条
        BannerPager banner = view.findViewById(R.id.tv_class_des);
        // 获取横幅轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(mActivity) * 250f / 640f);
        // 设置横幅轮播条的布局参数
        banner.setLayoutParams(params);
        // 设置横幅轮播条的广告图片队列
        banner.setImage(ImageList.getDefault());
        // 设置横幅轮播条的广告点击监听器
        banner.setOnBannerListener(new BannerClick());
        // 开始广告图片的轮播滚动
        banner.start();

        //初始化直播公开课
        // 从布局文件中获取循环视图
        rv_hor_live_class = view.findViewById(R.id.rv_hor_live_class);
        // 创建一个横向的瀑布流布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);
        // 设置循环视图的布局管理器
        rv_hor_live_class.setLayoutManager(manager);
        // 构建一个直播公开课信息列表的瀑布流适配器
        LiveClassAdapter liveClassAdapter = new LiveClassAdapter(mActivity, LiveClassInfo.getDefaultStag());
        // 设置瀑布流列表的点击监听器
        liveClassAdapter.setOnItemClickListener(liveClassAdapter);
        // 给rv_hor_live_class设置直播公开课信息瀑布流适配器
        rv_hor_live_class.setAdapter(liveClassAdapter);
        // 设置rv_hor_live_class的默认动画效果
        rv_hor_live_class.setItemAnimator(new DefaultItemAnimator());
        // 给rv_hor_live_class添加列表项之间的空白装饰
        rv_hor_live_class.addItemDecoration(new SpacesItemDecoration(1));
        return view;
    }

    @Override
    public void initData() {

    }

    // 一旦点击了广告图，就回调监听器的onBannerClick方法
    private class BannerClick implements BannerPager.BannerClickListener {
        @Override
        public void onBannerClick(int position) {
            String desc = String.format("您点击了第%d张图片", position + 1);
            Toast.makeText(mActivity, desc, Toast.LENGTH_LONG).show();
        }
    }
}
