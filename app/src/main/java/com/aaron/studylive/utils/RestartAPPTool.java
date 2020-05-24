package com.aaron.studylive.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Aaron on 2020/5/24
 * The current project is StudyLive
 *
 * @Describe:
 */
public class RestartAPPTool {
    /**
     * 重启整个APP
     * @param context
     */
    public static void restartAPP(Context context){
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
//        .getAppManager().finishAllActivity();
    }
}
