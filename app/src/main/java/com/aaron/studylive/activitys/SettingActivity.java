package com.aaron.studylive.activitys;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.VersionData;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.DestroyActivityUtil;
import com.aaron.studylive.utils.JudgeVersion;
import com.aaron.studylive.utils.L;

import java.io.File;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import butterknife.Bind;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.tv_back)
    TextView tv_back;

    private PackageInfo info;
    private String versionName;

    private TextView tvVersionName;
    private LinearLayout ll_setting_relogin;
    private LinearLayout ll_setting_version;
    private LinearLayout ll_setting_about;
    private LinearLayout ll_setting_policy;
    private LinearLayout ll_setting_feedback;
    private LinearLayout ll_setting_my_Account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityCollector.addActivity(this);

        showVersion();
        ClickEvents();
    }

    private void ClickEvents() {
        ll_setting_relogin = findViewById(R.id.ll_setting_relogin);
        ll_setting_relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ll_setting_relogin){
                    AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this)
                            .setTitle("温馨提示")
                            .setMessage("确定退出当前账号，并返回登录界面吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    BackToLogin();
                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog.show();

                }
            }
        });

        ll_setting_about = findViewById(R.id.ll_setting_about);
        ll_setting_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        ll_setting_policy = findViewById(R.id.ll_setting_policy);
        ll_setting_policy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ProTextActivity.class);
                startActivity(intent);
            }
        });

        ll_setting_feedback = findViewById(R.id.ll_setting_feedback);
        ll_setting_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });

        ll_setting_my_Account = findViewById(R.id.ll_setting_my_Account);
        ll_setting_my_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MyAccountActivity.class);
                startActivity(intent);
            }
        });




    }

    private void BackToLogin(){
        Intent intent = new Intent(SettingActivity.this,LoginContentActivity.class);
//        intent.putExtra("")  //传递用户名过去
        startActivity(intent);
        DestroyActivityUtil.destoryActivity("FlyMainActivity");
        finish();
    }

    private void showVersion() {
        tvVersionName = findViewById(R.id.tv_setting_version);
        ll_setting_version = findViewById(R.id.ll_setting_version);

        PackageManager pm = this.getPackageManager();
        try {
            info = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = info.versionName;
        tvVersionName.setText("Version： "+versionName);
        ll_setting_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查版本更新
                if (v.getId() == R.id.ll_setting_version){
                    getLocationVersion();
                }
            }
        });
    }

    /**
     * 更新功能的实现
     *
     */

    //判断并提示更新提示框
    public void getLocationVersion(  ) {

        L.d("getLocationVersion   =  " + VersionData.appVersion);
        try {
            if (JudgeVersion.compareVersion(VersionData.appVersion, versionName) == 1) { //网络版本大于本地版本
                L.d("网络版本大于本地版本");
                String title = "新版本更新";

                int fileSize = Integer.parseInt(VersionData.fileSize);
                String size = String.valueOf(fileSize/1048567);
                String message = VersionData.note.replace("；","；\n");
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(message+"\n"+"文件大小："+size + " MB")
                        .setNeutralButton("暂不更新", new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("更新", new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                L.d("下载");
                                startDownload(VersionData.appUrl);
                                Toast.makeText(SettingActivity.this, "正在下载更新...", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
                dialog.setCanceledOnTouchOutside(false);//可选
                dialog.setCancelable(false);//可选
            } else {
                L.d("网络版小于或等于本地版本");
                Toast.makeText(SettingActivity.this,"已经是最新的版本了！",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开apk文件,安装
     * @param filePath
     */
    private void openAPK(String filePath) {
        System.out.println("openAPK,开始安装");
        File file = new File(Uri.parse(filePath).getPath());
        if (!file.exists()) {
            System.out.println("下载的文件不存在!!!");
            return;
        }
//        System.out.println("要打开的文件:" + file.getAbsolutePath() + " 是否存在:" + file.exists());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (null != file) {
            try {
                //兼容7.0/24以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    System.out.println("api24/sdk7.0以上");
                    L.d("456行 mContext.getPackageName() =  "+SettingActivity.this.getPackageName());
                    //第二个参数是跟manifest文件里的authorities属性是一致的,当包名修改的时候配置里的参数也要修改
                    Uri uri = FileProvider.getUriForFile(SettingActivity.this, SettingActivity.this.getPackageName() + ".fileprovider",file);
                    L.d("458   uri   = "+uri);

                    System.out.println("要打开的文件:" + file.getAbsolutePath() + " 是否存在:" + file.exists());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    intent.setDataAndType(uri, "application/vnd.android.package-archive");

                    startActivity(intent);

                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    System.out.println("api24/sdk7.0以下");
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    startActivity(intent);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // 开启安装未知来源权限
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:"+getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 100);
    }


    /**
     * 开始下载
     * @param downloadUrl
     */
    private void startDownload(String downloadUrl) {
        // 从系统服务中获取下载管理器
        //获取系统服务
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(downloadUrl));
        request.setMimeType(mimeString);

        //设置通知栏的标题
        request.setTitle("下载");
        request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        //设置通知栏的message
        request.setDescription("Study Live 正在下载....");
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(true);
        File file = new File(Environment.DIRECTORY_DOWNLOADS + "/" + "studylive.apk");
        System.out.println(file.getAbsolutePath());
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(SettingActivity.this, Environment.DIRECTORY_DOWNLOADS, "studylive_update.apk");

        //进行下载,并取得下载id
        final long id = downloadManager.enqueue(request);
        //广播接收器
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("接收广播,broadcastReceiver.onReceive");
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                //获取下载的id,若没有则返回-1(第二个参数)
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //这里把传递的id和广播中获取的id进行对比是不是我们下载apk的那个id，如果是的话，就开始获取这个下载的路径
                if (ID == id) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor cursor = manager.query(query);
                    if (cursor.moveToFirst()) {
                        //获取文件下载路径
                        String filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        System.out.println("下载的文件路径:" + filePath);
                        //如果文件名不为空,说明文件已存在,自动安装
                        if (filePath != null) {
                            Uri uri = manager.getUriForDownloadedFile(id);
                            openAPK(filePath);
                        }
                    }
                    cursor.close();
                }
            }
        };
        //动态注册广播,当下载完成后，下载管理会发出DownloadManager.ACTION_DOWNLOAD_COMPLETE这个广播，
        // 并传递downloadId作为参数。通过接受广播我们可以打开对下载完成的内容进行操作
        System.out.println("动态注册广播");
        this.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }



}
