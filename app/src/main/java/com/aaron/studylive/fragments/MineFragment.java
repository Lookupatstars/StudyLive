package com.aaron.studylive.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.activitys.AboutActivity;
import com.aaron.studylive.activitys.MyAccountActivity;
import com.aaron.studylive.activitys.SettingActivity;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.LoginData;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.utils.ImageFormatUtil;

import butterknife.Bind;

/**
 * Created by Aaron on 2020/4/8
 * The current project is StudyLive
 *
 * @Describe:  个人信息
 *
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象

    @Bind(R.id.tv_my_name)
    TextView tv_my_name;

    @Bind(R.id.iv_my_head)
    ImageView iv_my_head;

    @Bind(R.id.ll_my_about)
    LinearLayout ll_my_about;

    @Bind(R.id.ll_my_setting)
    LinearLayout ll_my_setting;

    @Bind(R.id.ll_my_plan)
    LinearLayout ll_my_plan;

    @Bind(R.id.ll_my_upload)
    LinearLayout ll_my_upload;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        setHeadImg();
        setName();
        Justice();
        setClick();
    }

    //获取到用户名
    @SuppressLint("ResourceAsColor")
    private void setName(){
        if ( LoginData.getName()== null || LoginData.getName().equals("")){
            if ( LoginData.getUsername()== null || LoginData.getUsername().equals("")){
                tv_my_name.setText("未登录");
            }else {
                tv_my_name.setText(LoginData.getUsername());
            }
        }else {
            tv_my_name.setText(LoginData.getName());
        }
        tv_my_name.setTextColor(R.color.white);
        tv_my_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setHeadImg(){

        Bitmap img = LoginData.getImg();
        int min = Math.min(img.getHeight(), img.getWidth());
        img = ImageFormatUtil.createCircleImage(img,min);
        iv_my_head.setImageBitmap(img);

    }

    //通过permission进行判断是学生还是老师
    private void Justice(){

        if (LoginData.getRoleId() == 2){
            //学生
            ll_my_upload.setVisibility(View.GONE);
        }
        if (LoginData.getRoleId() == 3){
            //教师
            ll_my_plan.setVisibility(View.GONE);
        }
    }


    private void setClick(){
        ll_my_about.setOnClickListener(this);
        ll_my_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_about:
                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_my_setting:
                Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_my_plan:
//                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent3);
                break;
            case R.id.ll_my_upload:
//                Intent intent4 = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent4);
                break;
            case R.id.ll_my_comment:
//                Intent intent5 = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent5);
                break;
            case R.id.ll_my_message:
//                Intent intent6 = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent6);
                break;
            case R.id.ll_my_follow:
//                Intent intent7 = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent7);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
