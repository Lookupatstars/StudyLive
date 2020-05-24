package com.aaron.studylive.activitys;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.L;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {


    private PackageInfo info;
    private String versionName;

    private TextView tvVersionName;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActivityCollector.addActivity(this);

        tvVersionName = findViewById(R.id.tv_version_name);
        setVersion();

    }

    public void setVersion(){
        PackageManager pm = this.getPackageManager();

        try {
            info = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        versionName = info.versionName;

        L.d("versionName  是多少=  "+versionName);
        tvVersionName.setText("软件版本号： "+versionName);
    }
}
