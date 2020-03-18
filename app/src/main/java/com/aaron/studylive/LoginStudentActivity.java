package com.aaron.studylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginStudentActivity extends AppCompatActivity {

    private static final String TAG = "LoginStudentActivity";

    private EditText et_student_user,et_student_passwd;
    private TextView tv_forgotpasswd;
    private Button btn_login,btn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        et_student_user = findViewById(R.id.et_student_user);
        et_student_passwd = findViewById(R.id.et_student_passwd);

        tv_forgotpasswd = findViewById(R.id.tv_forgotpasswd);

        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);

        btn_login.setOnClickListener(new LoginOnClickListener());
        btn_regist.setOnClickListener(new RegistClickListener());

    }

    //点击登录进入到主界面
    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_login){
                Intent intent = new Intent(LoginStudentActivity.this,MainActivity.class);
                startActivity(intent);
                LoginStudentActivity.this.finish();
            }
        }

    }

    //注册界面的点击按钮
    class RegistClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_regist){
                Intent intent = new Intent();
                intent.setClass(LoginStudentActivity.this,RegistActivity.class);
                startActivity(intent);
            }

        }
    }




}
