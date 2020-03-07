package com.aaron.studylive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.aaron.studylive.widget.BannerPager.BannerClickListener;

@SuppressLint("DefaultLocale")
public class HomeActivity extends AppCompatActivity implements BannerClickListener {

    private final static String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    // 一旦点击了广告图，就回调监听器的onBannerClick方法
    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        Toast.makeText(this, desc, Toast.LENGTH_LONG).show();
    }


}
