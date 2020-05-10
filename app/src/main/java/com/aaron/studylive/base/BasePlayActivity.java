package com.aaron.studylive.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aaron.studylive.R;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/31.
 */
public abstract class BasePlayActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Vitamio.isInitialized(this);

        setContentView(getLayoutId());
        ButterKnife.bind(this);

        init();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_left);
    }

}
