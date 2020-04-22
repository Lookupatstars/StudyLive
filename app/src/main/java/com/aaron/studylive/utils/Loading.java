package com.aaron.studylive.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aaron.studylive.R;
import com.aaron.studylive.constant.GlobalVaries;

import butterknife.ButterKnife;


public class Loading {

    private static Loading instance;

    private Dialog mDialog;

    private View dialogView;

    private LayoutInflater inflater;

    private ImageView ivLoading;

    final GlobalVaries globalVaries = new GlobalVaries(); // 是否是在loading

    private Loading() {

    }

    private Loading(Context context) {
        inflater = LayoutInflater.from(context);
        mDialog = new Dialog(context, R.style.DialogProgress);
        dialogView = inflater.inflate(R.layout.dialog_loading, null);
        mDialog.setContentView(dialogView);
        ivLoading = ButterKnife.findById(dialogView, R.id.iv_loading);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.main_loading_anim);
        ivLoading.startAnimation(anim);
    }

    public static Loading getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new Loading(context);
                }
            }
        }
        return instance;
    }

    public void show() {
        try {
            if (!mDialog.isShowing()){
                mDialog.show();
            }
        } catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void hide() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }

    }

}

