package com.aaron.studylive.broadcast;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.aaron.studylive.utils.L;

import java.io.File;

import androidx.core.content.FileProvider;

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
            openAPK(context);
        }
    }

    private void openAPK(Context mContext) {
        System.out.println("openAPK,开始安装");
        String filePath = "/storage/emulated/0/Android/data/com.aaron.studylive/files/Download/studylive_update.apk";
        File file = new File(Uri.parse(filePath).getPath());
        if (!file.exists()) {
            System.out.println("下载的文件不存在!!!");
            return;
        }
        System.out.println("要打开的文件:" + file.getAbsolutePath() + " 是否存在:" + file.exists());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (null != file) {
            try {
                //兼容7.0/24以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    System.out.println("api24/sdk7.0以上");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    L.d("456行 mContext.getPackageName() =  "+mContext.getPackageName());
                    //第二个参数是跟manifest文件里的authorities属性是一致的,当包名修改的时候配置里的参数也要修改
                    Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider",file);
                    L.d("458   uri   = "+uri);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    //兼容8.0/26以上,需要安装权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        System.out.println("api26/sdk8.0以上");
                        Toast.makeText(mContext, "更新需要设置允许安装外部来源应用,将为您跳转设置界面",Toast.LENGTH_SHORT).show();
                        //这里不弄个定时器会下载完立刻打开设置界面,用户可能会有点懵逼
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
                        if (!mContext.getPackageManager().canRequestPackageInstalls()) {
                            //这个是8.0新api
                            Intent intent1 = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            mContext.startActivity(intent1);
                            return;
                        }
//                            }
//                        }, 3000);
                    }
                } else {
                    System.out.println("api24/sdk7.0以下");
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("打开安装界面");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    // 安装Apk
//    private void installApk(Context context) {
//        try {
//            Intent i = new Intent(Intent.ACTION_VIEW);
////    file:///storage/emulated/0/Android/data/com.aaron.studylive/files/Download/studylive_update.apk
//            String filePath = "/storage/emulated/0/Android/data/com.aaron.studylive/files/Download/studylive_update.apk";
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
