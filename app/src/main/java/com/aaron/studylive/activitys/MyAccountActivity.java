package com.aaron.studylive.activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.LoginData;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccountActivity extends AppCompatActivity {

    private TextView tv_account_username,tv_account_email,tv_account_role,tv_account_name,tv_account_phone,tv_account_createTime;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        tv_account_username = findViewById(R.id.tv_account_username);
        tv_account_email = findViewById(R.id.tv_account_email);
        tv_account_role = findViewById(R.id.tv_account_role);
        tv_account_name = findViewById(R.id.tv_account_name);
        tv_account_phone = findViewById(R.id.tv_account_phone);
        tv_account_createTime = findViewById(R.id.tv_account_createTime);

        tv_account_username.setText("用户名："+LoginData.getUsername());
        tv_account_email.setText("邮箱："+LoginData.getEmail());
        if (LoginData.getRoleId() == 3){
            tv_account_role.setText("身份：讲师" );
        }else {
            tv_account_role.setText("身份：学生" );
        }

        tv_account_name.setText("姓名："+LoginData.getName());
        if (LoginData.getPhone().equals("null")){
            tv_account_phone.setText("手机号：无" );
        }else {
            tv_account_phone.setText("手机号：" +LoginData.getPhone());
        }

        String time = LoginData.getCreate_time().replace("T"," ");
        tv_account_createTime.setText("注册时间："+time);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
