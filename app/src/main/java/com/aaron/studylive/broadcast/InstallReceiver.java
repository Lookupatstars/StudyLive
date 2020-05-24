package com.aaron.studylive.broadcast;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Aaron on 2020/5/24
 * The current project is StudyLive
 *
 * @Describe:
 */
public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//            installApk(context);
        }
    }

    // 安装Apk
//    private void installApk(Context context) {
//        try {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            String filePath = ConfigurationUtil.APK_PATH_ABSOULT+"GTLXKJ.apk";
//            System.out.println(filePath);
//            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
