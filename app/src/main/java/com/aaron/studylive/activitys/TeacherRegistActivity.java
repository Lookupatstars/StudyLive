package com.aaron.studylive.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.constant.GlobalVaries;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.database.StudentInfo;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.L;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherRegistActivity extends AppCompatActivity {

    private Button btn_reg_back,btn_reg_check,btn_reg_reg;
    private EditText et_reg_phone,et_reg_check,et_reg_name,et_reg_passwd,et_reg_conpasswd,et_reg_teacherid;
    private TextView tv_reg_pro;

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_teacher);

        ActivityCollector.addActivity(this);
        final GlobalVaries globalVaries = (GlobalVaries)this.getApplication(); // 验证码


        //事件注册的类
        initView();
        //点击按钮的实现类
        intiClick();

        //验证手机号
        btn_reg_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_reg_phone.getText().toString().trim();
                StudentInfo byPhoneInfo = sDB.queryByEmail(phone);
                if (v.getId() == R.id.btn_reg_check){
                    if (phone.equals("")){
                        Toast.makeText(TeacherRegistActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (phone.length() != 11){
                            Toast.makeText(TeacherRegistActivity.this,"请正确输入手机号",Toast.LENGTH_SHORT).show();
                            return;
                        }else{ //非空，位数正确
                            L.d("byPhoneInfo::"+byPhoneInfo);
//                            L.d("byPhoneInfo.phone::"+byPhoneInfo.phone);
                            if (byPhoneInfo == null){
                                // 生成六位随机数字的验证码
                                String str = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                                globalVaries.setVerifyCode(str);
                                // 弹出提醒对话框，提示用户六位验证码数字
                                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherRegistActivity.this);
                                builder.setTitle("请记住验证码");
                                builder.setMessage("手机号" + phone + "，本次验证码是" + str + "，请输入验证码");
                                builder.setPositiveButton("好的", null);
                                AlertDialog alert = builder.create();
                                alert.show();
                            }else { //数据库里存在有这个手机号
                                Toast.makeText(TeacherRegistActivity.this,"手机号已存在，请检查输入是否正确",Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    }
                }
            }
        });

        //注册按钮
        btn_reg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_reg_reg){

                    String teacher_phone = et_reg_phone.getText().toString().trim();
                    String teacher_check = et_reg_check.getText().toString().trim();
                    String teacher_name = et_reg_name.getText().toString().trim();
                    String teacher_passwd = et_reg_passwd.getText().toString().trim();
                    String teacher_conpasswd = et_reg_conpasswd.getText().toString().trim();
                    String teacher_id = et_reg_teacherid.getText().toString().trim();

                    String verifyCode = globalVaries.getVerifyCode();
                    L.d("verifyCode:"+verifyCode);
                    L.d("student_check:"+teacher_check);

                    //判断是否为空
                    //判断数据库中是否有重复的手机号，如果有重复的可以提示找回密码
                    if(teacher_phone.equals("")){
                        Toast.makeText(TeacherRegistActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (teacher_phone.length()!= 11){
                        Toast.makeText(TeacherRegistActivity.this,"手机号有误，请重新输入",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (teacher_check.equals("")){
                        Toast.makeText(TeacherRegistActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }  else if (!teacher_check.equals(verifyCode)){
                        Toast.makeText(TeacherRegistActivity.this,"请输入正确的验证码！",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (teacher_name.equals("")){
                        Toast.makeText(TeacherRegistActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (teacher_passwd.equals("") || teacher_conpasswd.equals("")){
                        Toast.makeText(TeacherRegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (teacher_passwd.length()<6 || teacher_passwd.length()>16){
                        Toast.makeText(TeacherRegistActivity.this,"密码长度不对",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (teacher_passwd.equals(teacher_conpasswd) && teacher_check.equals(verifyCode)){

                        //两次密码相同，注册
                        //创建一个实体的类，把信息注册进入StunetInfo
                        StudentInfo studentInfo = new StudentInfo();
                        studentInfo.name =  teacher_name;
                        studentInfo.phone = teacher_phone;
                        studentInfo.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                        studentInfo.roleId = 3;
                        sDB.insert(studentInfo);

                        Toast.makeText(TeacherRegistActivity.this,"注册成功，您已同意服务协议",Toast.LENGTH_SHORT).show();
                        //注册成功后直接登陆.

                        Intent intent=new Intent(TeacherRegistActivity.this, LoginContentActivity.class);
                        startActivity(intent);
                        finish();

                    }else if (teacher_passwd != teacher_conpasswd){
                        Toast.makeText(TeacherRegistActivity.this,"两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        });  //注册按钮


    }

    //点击按钮的实现类
    private void intiClick() {
        btn_reg_back.setOnClickListener(new BackLogin()); //返回按钮
        tv_reg_pro.setOnClickListener(new ProText());  //协议点击
//        btn_reg_reg.setOnClickListener(new Regists());  //注册按钮
//        btn_reg_check.setOnClickListener(new CheckCode());  //手机号验证按钮

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
        et_reg_teacherid = findViewById(R.id.et_reg_teacherid);

    }

    //返回按钮
    private class BackLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reg_back){
                Intent intent = new Intent(TeacherRegistActivity.this,LoginContentActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
//
//    //手机号验证功能
//    private class CheckCode implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//             String phone = et_reg_phone.getText().toString().trim();
//            if (v.getId() == R.id.btn_reg_check){
//                if (phone.equals("")){
//                    Toast.makeText(StudentRegistActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (phone.length()<11){
//                    Toast.makeText(StudentRegistActivity.this,"请正确输入手机号",Toast.LENGTH_SHORT).show();
//                    return;
//                }else{
//                    // 生成六位随机数字的验证码
//                    String str = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
////                    globalVaries.setVerifyCode(str);
//                    // 弹出提醒对话框，提示用户六位验证码数字
//                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentRegistActivity.this);
//                    builder.setTitle("请记住验证码");
//                    builder.setMessage("手机号" + phone + "，本次验证码是" + str + "，请输入验证码");
//                    builder.setPositiveButton("好的", null);
//                    AlertDialog alert = builder.create();
//                    alert.show();
//
//                }
//            }
//        }
//    }

    //点击协议
    private class ProText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //点击服务协议的显示内容
            Toast.makeText(TeacherRegistActivity.this,"您点击了服务条约",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
