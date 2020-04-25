package com.aaron.studylive.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.activitys.AboutActivity;
import com.aaron.studylive.R;
import com.aaron.studylive.activitys.SettingActivity;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.LoginStudentInfo;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.utils.L;

import butterknife.Bind;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象
    LoginStudentInfo loginStudentInfo = new LoginStudentInfo();

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.ll_about_fly)
    LinearLayout ll_about_fly;

    @Bind(R.id.ll_setting)
    LinearLayout ll_setting;

    @Bind(R.id.ll_myplan)
    LinearLayout ll_myplan;

    @Bind(R.id.ll_myupload)
    LinearLayout ll_myupload;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        setName();
        Justice();
        setClick();
    }

    //获取到用户名
    @SuppressLint("ResourceAsColor")
    private void setName(){
        L.d("loginStudentInfo.getName()  = "+loginStudentInfo.getName());
        L.d("loginStudentInfo.getPhone() = "+loginStudentInfo.getPhone());
        if ( loginStudentInfo.getName() == null || loginStudentInfo.getName().equals("")){
            if ( loginStudentInfo.getPhone() == null || loginStudentInfo.getPhone().equals("")){
                tv_name.setText("未登录");
            }else {
                tv_name.setText(loginStudentInfo.getPhone());
            }
        }else {
            tv_name.setText(loginStudentInfo.getName());
        }
        tv_name.setTextColor(R.color.raise_bg);
    }

    //通过permission进行判断是学生还是老师
    private void Justice(){

        if (LoginStudentInfo.getPermission() == 1){
            //学生
            ll_myupload.setVisibility(View.GONE);
        }
        if (LoginStudentInfo.getPermission() == 2){
            //教师
            ll_myplan.setVisibility(View.GONE);
        }
    }


    private void setClick(){
        ll_about_fly.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_about_fly:
                toast("点击了关于我们");
                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_setting:
                toast("点击了设置");
                Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                break;

            default:
                toast("没有点击住");
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        sDB = StudentDBhelper.getInstance(getActivity(), 2);
        // 恢复页面，则打开数据库连接
        sDB.openWriteLink();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        sDB.closeLink();
    }

    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
