package com.aaron.studylive.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.L;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private PackageInfo info;
    private String versionName;

    private TextView tvVersionName;

    private LinearLayout ll_setting_relogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityCollector.addActivity(this);
        showVersion();
        ClickEvents();
    }

    private void ClickEvents() {

        ll_setting_relogin = findViewById(R.id.ll_setting_relogin);
        ll_setting_relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ll_setting_relogin){
                    AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this)
                            .setTitle("温馨提示")
                            .setMessage("确定注销账号，并返回登录界面吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
//                                    intent.putExtra("")  //传递用户名过去
                                    startActivity(intent);
                                    finish();
                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog.show();

                }
            }
        });
    }

    private void showVersion() {
        tvVersionName = findViewById(R.id.tv_setting_version);
        PackageManager pm = this.getPackageManager();

        try {
            info = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = info.versionName;
        L.d("versionName  是多少=  "+versionName);
        tvVersionName.setText("Version： "+versionName);
        tvVersionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查版本更新
            }
        });
    }


}
