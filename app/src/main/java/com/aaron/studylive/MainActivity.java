package com.aaron.studylive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DepartmentStoreActivity";
    private Bundle mBundle = new Bundle(); // 声明一个包裹对象
    private LinearLayout ll_container, ll_first, ll_second, ll_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}
