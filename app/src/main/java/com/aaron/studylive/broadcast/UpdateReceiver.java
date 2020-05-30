package com.aaron.studylive.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aaron.studylive.activitys.SplashActivity;
import com.aaron.studylive.utils.L;

/**
 * Created by Aaron on 2020/5/23
 * The current project is StudyLive
 *
 * @Describe:
 */
public class UpdateReceiver extends BroadcastReceiver {

//    private static final String TAG = UpdateReceiver.class.getSimpleName();
//
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getDataString();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {//接收升级广播
            L.d("onReceive:升级了一个安装包，重新启动此程序");
            if (packageName.equals("package:" + context.getPackageName())) {
                Intent intent2 = new Intent(context, SplashActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent2);
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {//接收安装广播
            L.d( "onReceive:安装了" + packageName);
            if (packageName.equals("package:" + context.getPackageName())) {
                /*SystemUtil.reBootDevice();*/
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //接收卸载广播
            L.d( "onReceive:卸载了" + packageName);
        }
    }

}
