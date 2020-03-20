package com.aaron.studylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.bean.StudentInfo;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.utils.DateUtil;

public class RegistActivity extends AppCompatActivity {

    private Button btn_reg_back,btn_reg_check,btn_reg_reg;
    private EditText et_reg_phone,et_reg_check,et_reg_name,et_reg_passwd,et_reg_conpasswd;
    private TextView tv_reg_pro;

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //事件注册的类
        initView();

        btn_reg_back.setOnClickListener(new BackLogin()); //返回按钮
        tv_reg_pro.setOnClickListener(new ProText());  //协议点击
        btn_reg_reg.setOnClickListener(new Regists());  //注册按钮
        btn_reg_check.setOnClickListener(new CheckCode());  //手机号验证按钮



    }

    //事件注册的类
    private void initView(){
        //Button按钮的事件注册
        btn_reg_back = findViewById(R.id.btn_reg_back);
        btn_reg_check = findViewById(R.id.btn_reg_check);
        btn_reg_reg = findViewById(R.id.btn_reg_reg);
        tv_reg_pro = findViewById(R.id.tv_reg_pro);

        //EditText的事件注册
        et_reg_phone = findViewById(R.id.et_reg_phone);
        et_reg_check = findViewById(R.id.et_reg_check);
        et_reg_name = findViewById(R.id.et_reg_name);
        et_reg_passwd = findViewById(R.id.et_reg_passwd);
        et_reg_conpasswd = findViewById(R.id.et_reg_conpasswd);
    }

    //返回按钮
    private class BackLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reg_back){
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    //点击协议
    private class ProText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //点击服务协议的显示内容
            Toast.makeText(RegistActivity.this,"您点击了服务条约",Toast.LENGTH_SHORT).show();
        }
    }

    private class Regists implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reg_reg){

                String student_phone = et_reg_phone.getText().toString().trim();
                String student_name = et_reg_name.getText().toString().trim();
                String student_passwd = et_reg_passwd.getText().toString().trim();
                String student_conpasswd = et_reg_conpasswd.getText().toString().trim();

                //判断是否为空
                if(student_phone.equals("")){
                    Toast.makeText(RegistActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }else if (student_name.equals("")){
                    Toast.makeText(RegistActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else if (student_passwd.equals("")||student_conpasswd.equals("")){
                    Toast.makeText(RegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if (student_passwd.equals(student_conpasswd)){
                    //两次密码相同，注册
                    //创建一个实体的类，把信息注册进入StunetInfo
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.name = student_name;
                    studentInfo.phone = student_phone;
                    studentInfo.password = student_passwd;
                    studentInfo.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                    // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
                    sDB.insert(studentInfo);

                    Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    //注册成功后返回登录页面
                    Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
                    //intent.putExtra(,);//可以填入用户信息，如ID等
                    startActivity(intent);
                    finish();

                }else if (student_passwd.equals("")!=student_conpasswd.equals("")){
                    Toast.makeText(RegistActivity.this,"两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    //手机号验证功能
    private class CheckCode implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
