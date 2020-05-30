package com.aaron.studylive.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aaron.studylive.R;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private Button btn_feedback_submit;
    private EditText et_feedback_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        btn_feedback_submit = findViewById(R.id.btn_feedback_submit);
        et_feedback_content = findViewById(R.id.et_feedback_content);

        btn_feedback_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_feedback_content.getText().toString().trim().isEmpty() && !et_feedback_content.getText().toString().trim().equals("")){
                    et_feedback_content.setText("");
                    Toast.makeText(FeedbackActivity.this,"感谢您的反馈，我们会尽快处理！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FeedbackActivity.this,"请输入您的反馈信息",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
