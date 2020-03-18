package com.aaron.studylive;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.aaron.studylive.constant.ImageList;
import com.aaron.studylive.utils.Utils;
import com.aaron.studylive.widget.BannerPager;
import com.aaron.studylive.widget.BannerPager.BannerClickListener;

@SuppressLint("DefaultLocale")
public class HomeActivity extends AppCompatActivity implements BannerClickListener {

    private final static String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("未来的搜索位置");
        // 使用tl_head替换系统自带的ActionBar
        setSupportActionBar(tl_head);

        initBanner();
    }

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
