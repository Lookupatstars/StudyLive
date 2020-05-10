package com.aaron.studylive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.ListView;

@SuppressLint("DefaultLocale")
public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity";
    private RecyclerView rv_hor_live_class;
    private ListView lv_love_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_list);

        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("未来的搜索位置");
        // 使用tl_head替换系统自带的ActionBar
        setSupportActionBar(tl_head);



    }


}
