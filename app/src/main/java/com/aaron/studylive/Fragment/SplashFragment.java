package com.aaron.studylive.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.studylive.R;

public class SplashFragment extends Fragment {

    private ImageView mIvContent;
    private int mResId;

    private static final String BUNDLE_KEY_Res_Id = "key_res_id";


    public static SplashFragment newInstance(int resId){
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_Res_Id,resId);
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
            mResId =  arguments.getInt(BUNDLE_KEY_Res_Id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvContent = view.findViewById(R.id.iv_content);
        mIvContent.setImageResource(mResId);
        mIvContent.setTag(mResId);
    }
}
