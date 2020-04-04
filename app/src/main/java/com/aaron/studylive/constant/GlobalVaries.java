package com.aaron.studylive.constant;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Aaron on 2020/2/26
 * The current project is StudyLive
 *
 * @Describe: 全局静态变量、、XUtils 类的Application的初始化
 */
public class GlobalVaries extends Application {
    private  String verifyCode;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化XUtils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
