package com.aaron.studylive.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.aaron.studylive.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.Bind;

public class MyCommentActivity extends AppCompatActivity {

    @Bind(R.id.lv_my_comment_list)
    ListView mListView;

    @Bind(R.id.iv_my_comment_back)
    ImageView iv_my_comment_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);

        iv_my_comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
