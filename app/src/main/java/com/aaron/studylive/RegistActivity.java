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

public class RegistActivity extends AppCompatActivity {

    private Button btn_reg_back,btn_reg_check,btn_reg_reg;
    private EditText et_reg_phone,et_reg_check,et_reg_name,et_reg_passwd,et_reg_conpasswd;
    private TextView tv_reg_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //事件注册的类
        initView();
        btn_reg_back.setOnClickListener(new BackLogin());
        tv_reg_pro.setOnClickListener(new ProText());

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

    private class ProText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //点击服务协议的显示内容
            Toast.makeText(RegistActivity.this,"您点击了服务条约",Toast.LENGTH_SHORT).show();
        }
    }
}
