package com.aaron.studylive.activitys;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aaron.studylive.R;

public class ForgetPasswdActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasswd);
        btn_back = findViewById(R.id.btn_forgetpasswd_back);

        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_forgetpasswd_back:
                Intent intent = new Intent(ForgetPasswdActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }
}
