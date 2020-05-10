package com.aaron.studylive;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.aaron.studylive.adapter.ClassPagerAdapter;

import java.util.ArrayList;

public class DiscussActivity extends AppCompatActivity {

    private final static String TAG = "DiscussActivity";
    private ViewPager vp_content; // 定义一个翻页视图对象
    private TabLayout tab_title; // 定义一个标签布局对象
    private ArrayList<String> mTitleArray = new ArrayList<String>(); // 标题文字队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        Toolbar tl_head = findViewById(R.id.tl_head);
        // 使用tl_head替换系统自带的ActionBar
        setSupportActionBar(tl_head);

        mTitleArray.add("热门");
        mTitleArray.add("最新");
        mTitleArray.add("等待回答");

        // 从布局文件中获取名叫vp_content的翻页视图
        vp_content = findViewById(R.id.vp_content);

        initTabLayout(); // 初始化标签布局
        initTabViewPager(); // 初始化标签翻页

    }

    // 初始化标签布局
    private void initTabLayout() {
        // 从布局文件中获取名叫tab_title的标签布局
        tab_title = findViewById(R.id.tab_title);

        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(2)));

        // 给tab_title添加标签选中监听器，该监听器默认绑定了翻页视图vp_content
        tab_title.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_content));
    }

    // 初始化标签翻页
    private void initTabViewPager() {
        // 构建一个分类信息的翻页适配器
        ClassPagerAdapter adapter = new ClassPagerAdapter(
                getSupportFragmentManager(), mTitleArray);
        // 给vp_content设置分类翻页适配器
        vp_content.setAdapter(adapter);
        // 给vp_content添加页面变更监听器
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中tab_title指定位置的标签
                tab_title.getTabAt(position).select();
            }
        });
    }
}
