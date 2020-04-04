package com.aaron.studylive;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.aaron.studylive.adapter.LiveClassAdapter;
import com.aaron.studylive.adapter.LoveClassAdapter;
import com.aaron.studylive.bean.LiveClassInfo;
import com.aaron.studylive.bean.LoveClassInfo;
import com.aaron.studylive.constant.ImageList;
import com.aaron.studylive.utils.Utils;
import com.aaron.studylive.widget.BannerPager;
import com.aaron.studylive.widget.BannerPager.BannerClickListener;
import com.aaron.studylive.widget.SpacesItemDecoration;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity";
    private RecyclerView rv_hor_live_class;
    private ListView lv_love_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//
//        Toolbar tl_head = findViewById(R.id.tl_head);
//        tl_head.setTitle("未来的搜索位置");
//        // 使用tl_head替换系统自带的ActionBar
//        setSupportActionBar(tl_head);

    }


}
