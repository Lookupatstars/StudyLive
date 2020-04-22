package com.aaron.studylive.activitys;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aaron.studylive.R;

/**
 *
 * 可以设置只有一个登录界面
 * 通过数据库判别是否是老师
 * 因为老师和学生的界面差不多
 *
 */

public class LoginActivity extends ActivityGroup implements OnClickListener {

    private static final String TAG = "LoginActivity";
    private Bundle mBundle = new Bundle(); // 声明一个包裹对象
    private LinearLayout ll_student,ll_teacher,ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取登录界面的内容布局
        ll_content = findViewById(R.id.ll_content);
        //获取三个tab标签布局
        ll_student = findViewById(R.id.ll_student);
//        ll_teacher = findViewById(R.id.ll_teacher);

        // 给标签注册点击监听器
        ll_student.setOnClickListener(this);
//        ll_teacher.setOnClickListener(this);

        mBundle.putString("tag", TAG); // 往包裹中存入名叫tag的标记串
        changeContainerView(ll_student); // 默认显示第一个标签的内容视图


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_student) {
            changeContainerView(v); // 点击了哪个标签，就切换到该标签对应的内容视图
        }
    }

//    // 内容视图改为展示指定的视图
    private void changeContainerView(View v) {
        // 取消选中的标签
        ll_student.setSelected(false);
//        ll_teacher.setSelected(false);
        v.setSelected(true); // 选中指定标签
        if (v == ll_student) {
            // 切换到第一个活动页面
            toActivity("student", LoginContentActivity.class);
        }
//        else if (v == ll_teacher) {
//            // 切换到第二个活动页面
//            toActivity("teacher", LoginTeacherActivity.class);
//        }
    }

    // 把内容视图切换到对应的Activity活动页面
    private void toActivity(String label, Class<?> cls) {
        // 创建一个意图，并存入指定包裹
        Intent intent = new Intent(this, cls).putExtras(mBundle);
        // 移除内容框架下面的所有下级视图
        ll_content.removeAllViews();
        // 启动意图指向的活动，并获取该活动页面的顶层视图
        View v =  getLocalActivityManager().startActivity(label, intent).getDecorView();
        // 设置内容视图的布局参数
        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 把活动页面的顶层视图（即内容视图）添加到内容框架上
        ll_content.addView(v);
    }
}
