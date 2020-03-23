package com.aaron.studylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnFocusChangeListener;

import com.aaron.studylive.bean.StudentInfo;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.L;

public class LoginStudentActivity extends AppCompatActivity{

    private static final String TAG = "LoginStudentActivity";

    private static final int REQUEST_CODE_TO_REG = 0;

    private EditText et_student_user,et_student_passwd;
    private TextView tv_forgotpasswd;
    private CheckBox cb_remember;
    private Button btn_login,btn_regist;

    //记住密码，默认为false
    private boolean isRemember = false;
    private String mVerifyCode; // 验证码
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
        cb_remember = findViewById(R.id.cb_remember);

        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);

        // 给用户名编辑框添加文本变化监听器
//        et_student_user.addTextChangedListener(new JumpTextWatcher(et_student_user, et_student_passwd));
        // 给密码编辑框添加文本变化监听器
//        et_student_passwd.addTextChangedListener(new JumpTextWatcher(et_student_passwd, btn_login));

        btn_login.setOnClickListener(new LoginOnClickListener());
        btn_regist.setOnClickListener(new RegistClickListener());

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

    //点击登录进入到主界面
    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_login){
                //判断user和password
                if(et_student_user==null){
                    Toast.makeText(LoginStudentActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                } else if (et_student_passwd.equals("")){
                    Toast.makeText(LoginStudentActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //对比数据库的用户名密码
                //如果比对失败，提示注册

                //实现记住密码功能
                // 创建一个用户信息实体类
                StudentInfo info = new StudentInfo();
                info.phone = et_student_user.getText().toString().trim();
                info.password = et_student_passwd.getText().toString().trim();
                info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
                sDB.insert(info);
                L.d("insert is exe : "+sDB);

                Intent intent = new Intent(LoginStudentActivity.this,MainActivity.class);
                startActivity(intent);
                LoginStudentActivity.this.finish();



            }
        }
    }


    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）

    private class PasswdFocusListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String phone = et_student_user.getText().toString();
            L.d("onFocusChange");
            // 判断是否是密码编辑框发生焦点变化
//            if (v.getId() == R.id.et_reg_passwd) {
                // 用户已输入手机号码，且密码框获得焦点
                L.d("v.getId() == R.id.et_reg_passwd");
                if (phone.length() > 0 && hasFocus) {
                    // 根据手机号码到数据库中查询用户记录
                    StudentInfo info = sDB.queryByPhone(phone);
                    L.d("StudentInfo Info: "+info);
                    if (info != null) {
                        // 找到用户记录，则自动在密码框中填写该用户的密码
                        et_student_passwd.setText(info.password);
                    }
                }
//            }
        }
    }


    //注册界面的点击按钮
    class RegistClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_regist){
                Intent intent = new Intent();
                intent.setClass(LoginStudentActivity.this,RegistActivity.class);
                startActivityForResult(intent,REQUEST_CODE_TO_REG);
            }
        }
    }

    //接收返回数据
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        if (requestCode == REQUEST_CODE_TO_REG){
            if (resultCode == RESULT_OK){
                //从意图中取出名为phone的字符串
                String phone = data.getStringExtra("phone");
                et_student_user.setText(phone);
            }
        }
    }


}
