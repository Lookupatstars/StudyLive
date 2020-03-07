package com.aaron.studylive;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.app.ActivityGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends ActivityGroup implements OnClickListener{

    private static final String TAG = "DepartmentStoreActivity";
    private Bundle mBundle = new Bundle(); // 声明一个包裹对象
    private LinearLayout ll_container, ll_first, ll_second, ll_third,ll_forth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 从布局文件中获取名叫ll_container的线性布局，用于存放内容视图
        ll_container = findViewById(R.id.ll_container);

        // 获取标签的线性布局
        ll_first = findViewById(R.id.ll_first);
        ll_second = findViewById(R.id.ll_second);
        ll_third = findViewById(R.id.ll_third);
        ll_forth = findViewById(R.id.ll_forth);

        // 给标签注册点击监听器
        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        ll_forth.setOnClickListener(this);

        mBundle.putString("tag", TAG); // 往包裹中存入名叫tag的标记串
        changeContainerView(ll_first); // 默认显示第一个标签的内容视图

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_first || v.getId() == R.id.ll_second || v.getId() == R.id.ll_third) {
            changeContainerView(v); // 点击了哪个标签，就切换到该标签对应的内容视图
        }
    }

    // 内容视图改为展示指定的视图
    private void changeContainerView(View v) {
        // 取消选中的标签
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        ll_forth.setSelected(false);
        v.setSelected(true); // 选中指定标签
        if (v == ll_first) {
            // 切换到第一个活动页面DepartmentHomeActivity
            toActivity("first", MainActivity.class);
        } else if (v == ll_second) {
            // 切换到第二个活动页面DepartmentClassActivity
            toActivity("second", MainActivity.class);
        } else if (v == ll_third) {
            // 切换到第三个活动页面DepartmentCartActivity
            toActivity("third", MainActivity.class);
        } else if (v == ll_forth){
            toActivity("forth",MainActivity.class);
        }
    }

    // 把内容视图切换到对应的Activity活动页面
    private void toActivity(String label, Class<?> cls) {
        // 创建一个意图，并存入指定包裹
        Intent intent = new Intent(this, cls).putExtras(mBundle);
        // 移除内容框架下面的所有下级视图
        ll_container.removeAllViews();
        // 启动意图指向的活动，并获取该活动页面的顶层视图
        View v =  getLocalActivityManager().startActivity(label, intent).getDecorView();
        // 设置内容视图的布局参数
        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 把活动页面的顶层视图（即内容视图）添加到内容框架上
        ll_container.addView(v);
    }


}
