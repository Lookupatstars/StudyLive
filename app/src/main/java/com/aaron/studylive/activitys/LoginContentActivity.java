package com.aaron.studylive.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.LoginStudentInfo;
import com.aaron.studylive.database.StudentInfo;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.L;

public class LoginContentActivity extends AppCompatActivity{
    private static final String TAG = "LoginStudentActivity";

    private EditText et_student_user,et_student_passwd;
    private TextView tv_forgotpasswd;
    private Button btn_login,btn_regist;

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //事件注册以及点击的实现
        initViewAndClick();

    }

    //事件注册以及点击的实现
    private void initViewAndClick() {
        et_student_user = findViewById(R.id.et_student_user);
        et_student_passwd = findViewById(R.id.et_student_passwd);
        tv_forgotpasswd = findViewById(R.id.tv_forgotpasswd);

        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);

        // 给用户名编辑框添加文本变化监听器
//        et_student_user.addTextChangedListener(new JumpTextWatcher(et_student_user, et_student_passwd));
        // 给密码编辑框添加文本变化监听器
//        et_student_passwd.addTextChangedListener(new JumpTextWatcher(et_student_passwd, btn_login));

        btn_login.setOnClickListener(new LoginOnClickListener());
        btn_regist.setOnClickListener(new RegistClickListener());
        tv_forgotpasswd.setOnClickListener(new ForgetPasswd());

        //监听密码框是否获取到焦点
        et_student_passwd.setOnFocusChangeListener(new PasswdFocusListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        sDB = StudentDBhelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        sDB.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        sDB.closeLink();
    }

    //注册界面的点击按钮
    class RegistClickListener implements View.OnClickListener{
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_regist:
                    new AlertDialog.Builder(getParent())
                            .setTitle("请选择注册类型").setMessage("请正确选择您的身份，点击进行注册！")
                            .setPositiveButton("教师", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent();
                                    intent1.setClass(LoginContentActivity.this, TeacherRegistActivity.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            })
                            .setNeutralButton("学生", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(LoginContentActivity.this, StudentRegistActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).create().show();
            }
        }
    }

    //点击登录进入到主界面
    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_login){
                //获取输入框中的手机号、密码
                String phone = et_student_user.getText().toString().trim();
                String passwd = et_student_passwd.getText().toString().trim();
                StudentInfo byPhoneInfo = sDB.queryByPhone(phone);
                L.d("1.登录对比手机号byPhoneInfo.phone::"+byPhoneInfo.phone);
                //判断user和password
                if(phone.equals("")){
                    Toast.makeText(LoginContentActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwd.equals("")){
                    Toast.makeText(LoginContentActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone.equals(byPhoneInfo.phone)){ //对比数据库的手机号（用户名
                    //对比数据库的用户名密码
                    //如果比对失败，提示注册

                    if (passwd.equals(byPhoneInfo.password)){

                        btn_login.setEnabled(true);//设置按钮可点击
                        //实现记住密码功能
                        // 创建一个用户信息实体类
                        StudentInfo info = new StudentInfo();
                        info.phone = phone;
                        info.password = passwd;
                        info.name = byPhoneInfo.name;
                        info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                        // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
                        sDB.insert(info);

                        L.d("LoginStudentActivity::"+byPhoneInfo.name);
                        L.d("LoginStudentActivity::"+byPhoneInfo.phone);

                        //设置登录信息，方便个人信息界面调用

                        LoginStudentInfo loginStudentInfo = new LoginStudentInfo();
                        loginStudentInfo.setsId((int) byPhoneInfo.rowid);
                        L.d("loginStudentInfo.getsId()"+loginStudentInfo.getsId());
                        loginStudentInfo.setName(byPhoneInfo.name);
                        L.d("loginStudentInfo.getName()"+loginStudentInfo.getName());
                        loginStudentInfo.setPhone(byPhoneInfo.phone);
                        L.d("loginStudentInfo.getPhone()"+loginStudentInfo.getPhone());
                        loginStudentInfo.setUpdate_time(byPhoneInfo.update_time);
                        L.d("loginStudentInfo.getUpdate_time()"+loginStudentInfo.getUpdate_time());
                        loginStudentInfo.setPermission(byPhoneInfo.permission);
                        L.d("loginStudentInfo.getUpdate_time()"+loginStudentInfo.getPermission());
                        loginStudentInfo.setTeacherid(byPhoneInfo.teacherid);
                        L.d("loginStudentInfo.getTeacherid()"+loginStudentInfo.getTeacherid());
                        //END
                        Intent intent = new Intent(LoginContentActivity.this, FlyMainActivity.class);
                        //把昵称，签名输入到界面   ？？？？？？？？？？
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginContentActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(LoginContentActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }


    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）
    private class PasswdFocusListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String phone = et_student_user.getText().toString();
            // 判断是否是密码编辑框发生焦点变化
            // 用户已输入手机号码，且密码框获得焦点
            if (phone.length() > 0 && hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                StudentInfo byPhoneInfo = sDB.queryByPhone(phone);
                L.d("3.获得焦点，查询手机号: "+byPhoneInfo);
                if (byPhoneInfo != null) {
                    // 找到用户记录，则自动在密码框中填写该用户的密码
                    et_student_passwd.setText(byPhoneInfo.password);
                    btn_login.setEnabled(true);//设置按钮可点击
                }else {
                    Toast.makeText(LoginContentActivity.this,"用户不存在！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    //忘记密码的点击
    private class ForgetPasswd implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginContentActivity.this, ForgetPasswdActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
