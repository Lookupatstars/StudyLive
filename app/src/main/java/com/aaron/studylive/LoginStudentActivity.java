package com.aaron.studylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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

    }

    //点击登录进入到主界面
    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_login){
                if(et_student_user==null){
//                    Toast.makeText();
                }
                //判断user和password
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


    // 定义一个编辑框监听器，在输入回车符时自动跳到下一个控件
//    private class JumpTextWatcher implements TextWatcher {
//        private EditText mThisView;  // 声明当前的编辑框对象
//        private View mNextView; // 声明下一个视图对象
//
//        public JumpTextWatcher(EditText vThis, View vNext) {
//            super();
//            mThisView = vThis;
//            if (vNext != null) {
//                mNextView = vNext;
//            }
//        }
//
//        // 在编辑框的输入文本变化前触发
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//        // 在编辑框的输入文本变化时触发
//        public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//        // 在编辑框的输入文本变化后触发
//        public void afterTextChanged(Editable s) {
//            String str = s.toString();
//            // 发现输入回车符或换行符
//            if (str.contains("\r") || str.contains("\n")) {
//                // 去掉回车符和换行符
//                mThisView.setText(str.replace("\r", "").replace("\n", ""));
//                if (mNextView != null) {
//                    // 让下一个视图获得焦点，即将光标移到下个视图
//                    mNextView.requestFocus();
//                    // 如果下一个视图是编辑框，则将光标自动移到编辑框的文本末尾
//                    if (mNextView instanceof EditText) {
//                        EditText et = (EditText) mNextView;
//                        // 让光标自动移到编辑框内部的文本末尾
//                        // 方式一：直接调用EditText的setSelection方法
//                        et.setSelection(et.getText().length());
//                        // 方式二：调用Selection类的setSelection方法
//                        //Editable edit = et.getText();
//                        //Selection.setSelection(edit, edit.length());
//                    }
//                }
//            }
//        }
//    }


}
