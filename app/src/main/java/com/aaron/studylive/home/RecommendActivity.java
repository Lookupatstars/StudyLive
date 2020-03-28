package com.aaron.studylive.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.adapter.LiveClassAdapter;
import com.aaron.studylive.bean.LiveClassInfo;
import com.aaron.studylive.constant.ImageList;
import com.aaron.studylive.utils.Utils;
import com.aaron.studylive.widget.BannerPager;
import com.aaron.studylive.widget.BannerPager.BannerClickListener;
import com.aaron.studylive.widget.SpacesItemDecoration;

public class RecommendActivity extends AppCompatActivity implements BannerClickListener {

    private final static String TAG = "RecommendActivity";
    private RecyclerView rv_hor_live_class;
    private ListView lv_love_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);


        //初始化轮播图
        initBanner();
        //初始化直播公开课  横向
        initLiveClass();

        //初始化猜你喜欢  纵向
//        initLoveClass();


    }

    /**
     //初始猜你喜欢
     private void initLoveClass() {
     ArrayList<LoveClassInfo> loveClassInfos = LoveClassInfo.getDefaultStag();
     // 构建一个队列的列表适配器
     LoveClassAdapter loveClassAdapter = new LoveClassAdapter(this, loveClassInfos);
     // 从布局视图中获取列表视图
     lv_love_class = findViewById(R.id.lv_love_class);
     // 设置列表适配器
     lv_love_class.setAdapter(loveClassAdapter);
     // 设置列表项的点击监听器
     loveClassAdapter.setOnItemClickListener(loveClassAdapter);
     // 从资源文件中获取分隔线的图形对象
     //         drawable = getResources().getDrawable(R.drawable.divider_red2);

     }
     */

    //初始化直播公开课
    private void initLiveClass() {
        // 从布局文件中获取循环视图
        rv_hor_live_class = findViewById(R.id.rv_hor_live_class);
        // 创建一个横向的瀑布流布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);
        // 设置循环视图的布局管理器
        rv_hor_live_class.setLayoutManager(manager);
        // 构建一个直播公开课信息列表的瀑布流适配器
        LiveClassAdapter liveClassAdapter = new LiveClassAdapter(this, LiveClassInfo.getDefaultStag());
        // 设置瀑布流列表的点击监听器
        liveClassAdapter.setOnItemClickListener(liveClassAdapter);
        // 给rv_hor_live_class设置直播公开课信息瀑布流适配器
        rv_hor_live_class.setAdapter(liveClassAdapter);
        // 设置rv_hor_live_class的默认动画效果
        rv_hor_live_class.setItemAnimator(new DefaultItemAnimator());
        // 给rv_hor_live_class添加列表项之间的空白装饰
        rv_hor_live_class.addItemDecoration(new SpacesItemDecoration(1));
    }

    //初始化轮播图
    private void initBanner() {
        // 从布局文件中获取名叫banner_pager的横幅轮播条
        BannerPager banner = findViewById(R.id.banner_pager);
        // 获取横幅轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        // 设置横幅轮播条的布局参数
        banner.setLayoutParams(params);
        // 设置横幅轮播条的广告图片队列
        banner.setImage(ImageList.getDefault());
        // 设置横幅轮播条的广告点击监听器
        banner.setOnBannerListener(this);
        // 开始广告图片的轮播滚动
        banner.start();
    }

    // 一旦点击了广告图，就回调监听器的onBannerClick方法
    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        Toast.makeText(this, desc, Toast.LENGTH_LONG).show();
    }

}
