package com.aaron.studylive.constant;

import android.app.Application;

/**
 * Created by Aaron on 2020/2/26
 * The current project is StudyLive
 *
 * @Describe: 全局静态变量
 */
public class GlobalVaries extends Application {
    private  String verifyCode;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
