package com.aaron.studylive.base;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/27.
 */
public abstract class BaseFragment extends Fragment {


    //获取布局ID
    protected abstract int getLayoutId();
    //初始化
    protected abstract void init();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }
}
