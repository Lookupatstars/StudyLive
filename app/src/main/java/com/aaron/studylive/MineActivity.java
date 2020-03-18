package com.aaron.studylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        LinearLayout ll_about = findViewById(R.id.ll_about);
        ll_about.setOnClickListener(new About());
    }

    private class About implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MineActivity.this,AboutActivity.class);
            startActivity(intent);
        }
    }
}
