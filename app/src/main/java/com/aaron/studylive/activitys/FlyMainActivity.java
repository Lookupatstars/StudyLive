package com.aaron.studylive.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.HttpRespData;
import com.aaron.studylive.events.UpdateManager;
import com.aaron.studylive.fragments.ClassFragment;
import com.aaron.studylive.fragments.DownloadFragment;
import com.aaron.studylive.fragments.HomeFragment;
import com.aaron.studylive.fragments.MineFragment;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.JudgeVersion;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.views.NoScrollViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.disposables.Disposable;


/**
 * Created by Aaron on 2020/2/24
 * The current project is StudyLive
 *
 * @Describe: 主页面 空布局
 *
 */

public class FlyMainActivity extends AppCompatActivity implements View.OnClickListener {

    //声明底部标签栏和界面
    private NoScrollViewPager mViewPager;
    private RelativeLayout mTabHome;
    private RelativeLayout mTabClass;
    private RelativeLayout mTabDownload;
    private RelativeLayout mTabMine;
    private TextView mTvHome;
    private TextView mTvClass;
    private TextView mTVDownload;
    private TextView mTvMine;
    //声明四个主要界面和界面列表
    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private ClassFragment mClassFragment;
    private DownloadFragment mDownloadFragment;
    private MineFragment mMineFragment;

    public Activity mActivity;
    public Context mContext;
    private UpdateManager mUpdateManager;


    private Drawable drawable;// 给底部标签栏设置图片
    private static int[] mainMenuList = { //底部标签栏图片
            R.drawable.home648a8a8a, R.drawable.discuss648a8a8a,
            R.drawable.course648a8a8a, R.drawable.mine648a8a8a
    };

    //更新
    private HttpRespData respData = new HttpRespData();
    private Disposable downDisposable;
    private ProgressBar progressBar;
    private TextView textView4;
    private Button upgrade;
    private long downloadLength = 0;
    private long contentLength = 0;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private PackageInfo info;
    private int versionCode;
    private String versionName;
    private View view; //弹窗的view
    private static long mDownloadId = 0; // 下载编号
    //end 更新 变量

    private final static String TAG = "SearchViewActivity";
    private TextView tv_desc;
    private SearchView.SearchAutoComplete sac_key; // 声明一个搜索自动完成的编辑框对象
    private String[] hintArray = {"iphone", "iphone8", "iphone8 plus", "iphone7", "iphone7 plus"};
    //end 搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_main);
        ActivityCollector.addActivity(this);

        mActivity = this;
        mContext = this;

        //初始化布局
        initLayoutView();
        //设置显示内容
        setupContent();
        //设置底部Tab的点击事件
        setupTabClick();

        new LineVersionAsyncTask().execute();

    }

    //初始化布局
    private void initLayoutView() {
        //初始化主界面的内容界面和底部Tab
        mViewPager = findViewById(R.id.vp_fly_content);
        mTabHome = findViewById(R.id.tab_fly_home);
        mTabClass = findViewById(R.id.tab_fly_class);
        mTabDownload = findViewById(R.id.tab_fly_download);
        mTabMine = findViewById(R.id.tab_fly_mine);
        //底部Tab的TextView的初始化
        mTvHome = findViewById(R.id.tv_fly_home);
        mTvClass = findViewById(R.id.tv_fly_class);
        mTVDownload = findViewById(R.id.tv_fly_download);
        mTvMine = findViewById(R.id.tv_fly_mine);


    }

    //设置主界面的显示内容
    private void setupContent() {
        //初始化四个Fragment
        initFragments();

        //四个Fragment的获取
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener); //界面监听
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }

    //初始化四个Fragment
    private void initFragments() {
        mFragments = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mClassFragment = new ClassFragment();
        mDownloadFragment = new DownloadFragment();
        mMineFragment = new MineFragment();

        mFragments.add(mHomeFragment);
        mFragments.add(mClassFragment);
        mFragments.add(mDownloadFragment);
        mFragments.add(mMineFragment);
    }

    //界面监听，当界面改变的时候，进行设置
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //界面被选中
        @Override
        public void onPageSelected(int position) {
            clearTabBackgroundWithTextColor();//每次点击之前，清除上次的点击

            switch (position) {
                case 0:
                    //设置DrawableTop 、DrawableLeft、DrawableRight、DrawableBottom图片
                    setDrawableTopImage(R.drawable.home641296db, mTvHome);
                    break;
                case 1:
                    setDrawableTopImage(R.drawable.discuss641296db, mTvClass);
                    break;
                case 2:
                    setDrawableTopImage(R.drawable.course641296db, mTVDownload);
                    break;
                case 3:
                    setDrawableTopImage(R.drawable.mine641296db, mTvMine);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //设置DrawableTop 、DrawableLeft、DrawableRight、DrawableBottom图片
    private void setDrawableTopImage(int id, TextView tv) {
        drawable = getResources().getDrawable(id);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        tv.setTextColor(getResources().getColor(R.color.tab_text_selected));
    }

    //每次点击之前，清除上次的点击
    private void clearTabBackgroundWithTextColor() {
        ClearImageAndColor(mTvHome, 0);
        ClearImageAndColor(mTvClass, 1);
        ClearImageAndColor(mTVDownload, 2);
        ClearImageAndColor(mTvMine, 3);
    }

    //每次点击之前，清除上次的点击
    private void ClearImageAndColor(TextView tv, int position) {
        drawable = getResources().getDrawable(mainMenuList[position]);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        //清除字体颜色
        tv.setTextColor(getResources().getColor(R.color.tab_text_normal));
    }

    //Tab的每次点击事件
    private void setupTabClick() {
        mTabHome.setOnClickListener(this);
        mTabClass.setOnClickListener(this);
        mTabDownload.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }

    //当点击到对应的Tab的时候，显示对应的界面
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_fly_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_fly_class:

                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_fly_download:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_fly_mine:
                mViewPager.setCurrentItem(3);
                break;
        }

    }

    /**
     * 更新功能的实现
     *
     */
    //线程
    private class LineVersionAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                String result = getData(HttpUrl.getInstance().getLineVersion());

                return result;
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getLineVersion(s);
        }
    }

    //请求版本数据
    public static String getData(String path) {
        String data = "";
        try {
            URL url = new URL(path);
            // 创建指定网络地址的HTTP连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            setConnHeader(conn, "GET", req_data);
            // 设置请求方式，常见的有GET和POST两种
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            // 设置连接超时时间
            conn.setConnectTimeout(5000);
            // 设置读写超时时间
            conn.setReadTimeout(10000);
            // 设置编码格式
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            data = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        L.d("请求 = " + data);
        return data;
    }

    //获取线上版本
    public void getLineVersion(String s) {
        L.d("getLineVersion = " + s);
        try {

            JSONObject object = new JSONObject(s);
            int code = object.getInt("code");
            if (code == 0){
                object = object.getJSONObject("content");
                L.d("appVersion = " + object.getString("appVersion"));
                L.d("note = " + object.getString("note"));
                L.d("appurl = " + object.getString("appUrl"));
                L.d("fileSize = " + object.getString("fileSize"));
                L.d("getLineVersion  1");

                respData.appVersion = object.getString("appVersion");
                L.d("getLineVersion  2");
                respData.appUrl = object.getString("appUrl");
                L.d("getLineVersion  3");
                respData.note = object.getString("note");
                L.d("getLineVersion  4");
                respData.fileSize = object.getString("fileSize");
                L.d("getLineVersion  5");
                respData.appId = object.getString("appId");
                L.d("getLineVersion  6");
            }

            getLocationVersion();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }//end

    /**
     * 判断并提示更新提示框
     */
    public void getLocationVersion( ) {
        PackageManager pm = this.getPackageManager();

        try {
            info = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = info.versionCode; //版本号 1
        versionName = info.versionName; // 版本名 1.0.1

        L.d("getLocationVersion   =  " + respData.appVersion);
        try {
            if (JudgeVersion.compareVersion(respData.appVersion, versionName) == 1) { //网络版本大于本地版本
                L.d("网络版本大于本地版本");
                String title = "新版本更新";
                String size = respData.fileSize;
                String message = respData.note;
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle(title)
                        .setMessage(message)
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
                                startDownload(respData.appUrl);
                                Toast.makeText(mContext, "正在下载更新...", Toast.LENGTH_SHORT).show();


                            }
                        }).show();
                dialog.setCanceledOnTouchOutside(false);//可选
                dialog.setCancelable(false);//可选
            } else {
                L.d("网络版小于或等于本地版本");
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
        System.out.println("要打开的文件:" + file.getAbsolutePath() + "是否存在:" + file.exists());
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
                                    restartAPP(mContext);
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
        restartAPP(mContext);
    }


    /**
     * 开始下载
     * @param downloadUrl
     */
    private void startDownload(String downloadUrl) {
        // 从系统服务中获取下载管理器
        //获取系统服务
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
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
        request.setAllowedOverRoaming(false);
        File file = new File(Environment.DIRECTORY_DOWNLOADS + "/" + "studylive.apk");
        System.out.println(file.getAbsolutePath());
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, "studylive_update.apk");

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
        mActivity.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void restartAPP(Context context){
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        finish();
    }

}