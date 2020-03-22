package com.aaron.studylive;

import android.app.Activity;
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
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Utils;

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
        //点击按钮的实现类
        intiClick();

    }

    //点击按钮的实现类
    private void intiClick() {
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

        // 给手机号码编辑框添加文本变化监听器
//        et_reg_phone.addTextChangedListener(new CountTextWatcher(et_reg_phone));
        // 给密码编辑框添加文本变化监听器
//        et_reg_passwd.addTextChangedListener(new CountTextWatcher(et_reg_passwd));


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

    //手机号验证功能
    private class CheckCode implements View.OnClickListener {
        @Override
        public void onClick(View v) {

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

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        sDB = StudentDBhelper.getInstance(this, 2);
        // 打开数据库帮助器的写连接
        sDB.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        sDB.closeLink();
    }

    //注册功能
    private class Regists implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reg_reg){

                String student_phone = et_reg_phone.getText().toString().trim();
                String student_check = et_reg_check.getText().toString().trim();
                String student_name = et_reg_name.getText().toString().trim();
                String student_passwd = et_reg_passwd.getText().toString().trim();
                String student_conpasswd = et_reg_conpasswd.getText().toString().trim();

                //判断是否为空
                //判断数据库中是否有重复的手机号，如果有重复的可以提示找回密码
                if(student_phone.equals("")){
                    Toast.makeText(RegistActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                } else if (student_phone.length()<11 || student_phone.length()>16){
                    Toast.makeText(RegistActivity.this,"手机号有误，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else if (student_check.equals("")){
                    Toast.makeText(RegistActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                } else if (student_name.equals("")){
                    Toast.makeText(RegistActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if (student_passwd.equals("")||student_conpasswd.equals("")){
                    Toast.makeText(RegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if (student_passwd.length()<6 || student_passwd.length()>16){
                    Toast.makeText(RegistActivity.this,"密码长度不对",Toast.LENGTH_SHORT).show();
                    return;
                }else if (student_passwd.equals(student_conpasswd) || student_check == ""){

                    //两次密码相同，注册
                    //创建一个实体的类，把信息注册进入StunetInfo
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.name =  student_name;
                    studentInfo.phone = student_phone;
                    studentInfo.password = student_passwd;
                    sDB.insert(studentInfo);

                    Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    //注册成功后返回登录页面.

                    Intent intent=new Intent(RegistActivity.this,LoginActivity.class);

                    intent.putExtra("phone",student_phone);
                    setResult(Activity.RESULT_OK,intent);//携带意图返回上一个界面
                    finish();


                }else if (student_passwd != student_conpasswd){
                    Toast.makeText(RegistActivity.this,"两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }
    }

    //注册实现










//    // 定义一个编辑框监听器，在输入文本未达到指定长度时，提示错误
//    private class CountTextWatcher implements TextWatcher {
//        private EditText mView; // 声明一个编辑框对象
//        private int mMaxLength; // 声明一个最大长度变量
//        private CharSequence mStr; // 声明一个文本串
//
//        public CountTextWatcher(EditText v) {
//            super();
//            mView = v;
//            // 通过反射机制获取编辑框的最大长度
//            mMaxLength = Utils.getMaxLength(v);
//        }
//
//        // 在编辑框的输入文本变化前触发
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//        // 在编辑框的输入文本变化时触发
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            mStr = s;
//        }
//
//        // 在编辑框的输入文本变化后触发
//        public void afterTextChanged(Editable s) {
//            // 输入文本未达到11位（如手机号码）时，提示
//            if (mStr.length() <= 11 && mMaxLength <= 11) {
//                Toast.makeText(RegistActivity.this,"手机号码长度不够！",Toast.LENGTH_SHORT).show();
////                Utils.hideAllInputMethod(EditHideActivity.this);
//            }
//
//            // 输入文本未达到6位（如登录密码）时，提示
//            if (mStr.length() <= 6 && mMaxLength <= 6) {
////                Utils.hideOneInputMethod(EditHideActivity.this, mView);
//                Toast.makeText(RegistActivity.this,"密码长度不够！",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


}
