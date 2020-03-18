package com.aaron.studylive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends AppCompatActivity {

    private Button btn_reg_back,btn_reg_check,btn_reg_reg,btn_reg_pro;
    private EditText et_reg_phone,et_reg_check,et_reg_name,et_reg_passwd,et_reg_conpasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //Button按钮的事件注册
        btn_reg_back = findViewById(R.id.btn_reg_back);
        btn_reg_check = findViewById(R.id.btn_reg_check);
        btn_reg_reg = findViewById(R.id.btn_reg_reg);
        btn_reg_pro = findViewById(R.id.btn_reg_pro);

        //EditText的事件注册
        et_reg_phone = findViewById(R.id.et_reg_phone);
        et_reg_check = findViewById(R.id.et_reg_check);
        et_reg_name = findViewById(R.id.et_reg_name);
        et_reg_passwd = findViewById(R.id.et_reg_passwd);
        et_reg_conpasswd = findViewById(R.id.et_reg_conpasswd);




    }
}
