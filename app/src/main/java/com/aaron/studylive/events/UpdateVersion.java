package com.aaron.studylive.events;

/**
 * Created by Aaron on 2020/5/16
 * The current project is StudyLive
 *
 * @Describe:  更新软件
 */
public class UpdateVersion {




    /**
     * 更新
     */

//
//    //线程
//    private class LineVersionAsyncTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//
//            try {
//                L.d("LineVersionAsyncTask 1");
//                String result  = getData(HttpUrl.getInstance().getLineVersion());
//
//                return result;
//            } catch (Exception e){
//                e.getMessage();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            L.d(" LineVersionAsyncTask 2");
//            getLineVersion(s);
//        }
//    }
//    //请求版本数据
//    public static String getData(String path){
//        String data = "";
//        L.d("getData 1 ");
//        try {
//
//            URL url = new URL(path);
//            // 创建指定网络地址的HTTP连接
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            setConnHeader(conn, "GET", req_data);
//            // 设置请求方式，常见的有GET和POST两种
//            conn.setRequestMethod("GET");
//            conn.setUseCaches(false);
//            // 设置连接超时时间
//            conn.setConnectTimeout(5000);
//            // 设置读写超时时间
//            conn.setReadTimeout(10000);
//            // 设置编码格式
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            StringBuffer buffer = new StringBuffer();
//            String line = "";
//            while ((line = in.readLine()) != null){
//                buffer.append(line);
//            }
//            data = buffer.toString();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        L.d("请求 = "+data);
//        return data;
//
//    }
//
//    //获取线上版本
//    public void getLineVersion(String s){
//        L.d("getLineVersion = "+s);
//        try {
//
//            JSONObject object = new JSONObject(s);
//
//            L.d("appVersion = "+object.getString("appVersion"));
//            L.d("note = "+object.getString("note"));
//            L.d("appurl = "+object.getString("appurl"));
//            L.d("fileSize = "+object.getString("fileSize"));
//            L.d("releaseTime = "+object.getString("releaseTime"));
//
//            L.d("getLineVersion  1");
//
//            respData.appVersion = object.getString("appVersion");
//            L.d("getLineVersion  2");
//            respData.appurl = object.getString("appurl");
//            L.d("getLineVersion  3");
//            respData.note = object.getString("note");
//            L.d("getLineVersion  4");
//            respData.fileSize = object.getString("fileSize");
//            L.d("getLineVersion  5");
//            respData.releaseTime = object.getString("releaseTime");
//            L.d("getLineVersion  6");
//
//            //gengxinde rukou
//            getLocationVersion();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //判断并提示
//
//    /**
//     * 判断并提示更新提示框
//     */
//    public void getLocationVersion( ) {
//        PackageManager pm = getActivity().getPackageManager();
//
//        try {
//            info = pm.getPackageInfo(getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        versionCode = info.versionCode; //版本号 1
//        versionName = info.versionName; // 版本名 1.0.1
//
//        L.d("getLocationVersion   =  " + respData.appVersion);
//        try {
//            if (JudgeVersion.compareVersion(respData.appVersion, versionName) == 1) { //网络版本大于本地版本
//                L.d("网络版本大于本地版本");
//                String title = "新版本更新";
//                String size = respData.fileSize;
//                String message = respData.note;
//                AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle
//                        (title).setMessage(message)
//                        .setNeutralButton("暂不更新", new DialogInterface
//                                .OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).setNegativeButton("更新", new DialogInterface
//                                .OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                L.d("下载");
//                                Toast.makeText(getActivity(), "正在下载...", Toast.LENGTH_SHORT).show();
//                                //动态询问是否授权
//                                int permission = ActivityCompat.checkSelfPermission(getActivity(),
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                                if (permission != PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE,
//                                            1);
//                                } else {
////                                    upgrade.setVisibility(View.INVISIBLE);
//                                    startDownload(respData.appurl);
//                                }
//
//                            }
//                        }).show();
//                dialog.setCanceledOnTouchOutside(false);//可选
//                dialog.setCancelable(false);//可选
//            } else {
//                L.d("网络版小于或等于本地版本");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
////
////    private void test(){
////        L.d("test 运行了");
////        Observable.create(new ObservableOnSubscribe<String>() {
////            @Override
////            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
////                OkHttpClient client = new OkHttpClient();
////                Request request = new Request.Builder()
////                        .url(respData.appurl)
////                        .build();
////
////                client.newCall(request).enqueue(new okhttp3.Callback() {
////                    @Override
////                    public void onFailure(Call call, IOException e) {
////                        emitter.onError(e);
////                    }
////
////                    @Override
////                    public void onResponse(Call call, Response response) throws IOException {
////                        String result="";
////                        if (response.body()!=null) {
////                            result=response.body().string();
////                        }else {
////                            //返回数据错误
////                            return;
////                        }
////                        emitter.onNext(result);
////                    }
////                });
//////                emitter.onNext("123");
////            }
////        }).subscribeOn(Schedulers.io())// 将被观察者切换到子线程
////                .observeOn(AndroidSchedulers.mainThread())// 将观察者切换到主线程
////                .subscribe(new Observer<String>() {
////                    private Disposable mDisposable;
////                    @Override
////                    public void onSubscribe(Disposable d) {
////                        mDisposable = d;
////                    }
////                    @Override
////                    public void onNext(String result) {
////                        if (result.isEmpty()){
////                            return;
////                        }
////                        //2.判断版本是否最新，如果不是最新版本则更新
////                        String downloadUrl=respData.appurl;
////                        String title="新版本更新";
////                        String size="新版本大小：未知";
////                        String msg=respData.note;
////                        PackageManager pm = getActivity().getPackageManager();
////
////                        try {
////                            info = pm.getPackageInfo(getActivity().getPackageName(),PackageManager.GET_ACTIVITIES);
////                        } catch (PackageManager.NameNotFoundException e) {
////                            e.printStackTrace();
////                        }
////                        versionCode = info.versionCode; //版本号 1
////                        versionName = info.versionName; // 版本名 1.0.1
////
////                        L.d("getLocationVersion   =  "+respData.appVersion);
////
////                        try {
////                            if (JudgeVersion.compareVersion(respData.appVersion,versionName) == 1){ //网络版本大于本地版本
////                                L.d("网络版本大于本地版本");
//////                            int version = getPackageManager().
//////                                    getPackageInfo(getPackageName(), 0).versionCode;
//////                            if (versionCode>version){
//////                                mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
//////                                mRefreshListView.addHeaderView(mHeaderView);
//////                                mBanner = ButterKnife.findById(mHeaderView,R.id.fb_banner);
////
////                                L.d("正在执行。。。。实现弹窗   2");
////                                AlertDialog.Builder mDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),R.style.Translucent_NoTitle);
////                                L.d("正在执行。。。。实现弹窗  3");
////                                mDialog.setView(view);
////                                mDialog.setCancelable(true);
////                                mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
////                                    @Override
////                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
////                                        return keyCode == KeyEvent.KEYCODE_BACK;
////                                    }
////                                });
////                                L.d("正在执行。。。。实现弹窗");
////                                upgrade= view.findViewById(R.id.button);
////                                TextView textView1= view.findViewById(R.id.textView1);
////                                TextView textView2= view.findViewById(R.id.textView2);
////                                TextView textView3= view.findViewById(R.id.textView3);
////                                textView4= view.findViewById(R.id.textView4);
////                                ImageView iv_close= view.findViewById(R.id.iv_close);
////                                progressBar= view.findViewById(R.id.progressBar);
////                                progressBar.setMax(100);
////                                textView1.setText(title);
////                                textView2.setText(size);
////                                textView3.setText(msg);
////                                upgrade.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        //动态询问是否授权
////                                        int permission = ActivityCompat.checkSelfPermission(getActivity(),
////                                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
////                                        if (permission != PackageManager.PERMISSION_GRANTED) {
////                                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE,
////                                                    1);
////                                        }else {
////                                            upgrade.setVisibility(View.INVISIBLE);
////                                            down(downloadUrl);
////                                        }
////                                    }
////                                });
////                                iv_close.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        finish();
//////                                        L.d(" 关闭了 ");
////                                    }
////                                });
////                                mDialog.show();
////                            }else {
////
////                            }
////                        } catch (PackageManager.NameNotFoundException e) {
////                            e.printStackTrace();
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                        mDisposable.dispose();
////                    }
////                    @Override
////                    public void onError(Throwable e) {
////                        test();
////                    }
////                    @Override
////                    public void onComplete() {
////
////                    }
////                });
////    }
//
//    //下载apk并更新进度条
////    private void down(String downloadUrl){
////        L.d("点击了升级，并进去");
////        Observable.create(new ObservableOnSubscribe<Integer>() {
////            @Override
////            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
////                startDownload(downloadUrl,emitter);
////            }
////        }).subscribeOn(Schedulers.io())// 将被观察者切换到子线程
////                .observeOn(AndroidSchedulers.mainThread())// 将观察者切换到主线程
////                .subscribe(new Observer<Integer>() {
////
////                    @Override
////                    public void onSubscribe(Disposable d) {
////                        downDisposable = d;
////                    }
////                    @Override
////                    public void onNext(Integer result) {
////                        //设置ProgressDialog 进度条进度
////                        progressBar.setProgress(result);
////                        textView4.setText(result+"%");
////                    }
////                    @Override
////                    public void onError(Throwable e) {
////                        Toast.makeText(getActivity(),"网络异常！请重新下载！",Toast.LENGTH_SHORT).show();
////                        upgrade.setEnabled(true);
////                    }
////                    @Override
////                    public void onComplete() {
////                        Toast.makeText(getActivity(),"服务器异常！请重新下载！",Toast.LENGTH_SHORT).show();
////                        upgrade.setEnabled(true);
////                    }
////                });
////    }
//
//
//    //下载apk
////    private void downApk(String downloadUrl,ObservableEmitter<Integer> emitter){
////        OkHttpClient client = new OkHttpClient();
////        Request request = new Request.Builder()
////                .url(downloadUrl)
////                .build();
////        client.newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, IOException e) {
////                //下载失败
//////                breakpoint(downloadUrl,emitter);
////                L.d("下载失败");
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) throws IOException {
//////                if (response.body() == null) {
//////                    //下载失败
//////                    breakpoint(downloadUrl,emitter);
//////                    return;
//////                }
////                InputStream is = null;
////                FileOutputStream fos = null;
////                byte[] buff = new byte[2048];
////                int len;
////                try {
////                    is = response.body().byteStream();
//////                    File file = createFile();
////                    File file = new File(Environment.DIRECTORY_DOWNLOADS + "/" + "studyLive.apk");
////                    fos = new FileOutputStream(file);
////                    long total = response.body().contentLength();
////                    contentLength=total;
////                    long sum = 0;
////                    while ((len = is.read(buff)) != -1) {
////                        fos.write(buff,0,len);
////                        sum+=len;
////                        int progress = (int) (sum * 1.0f / total * 100);
////                        //下载中，更新下载进度
////                        emitter.onNext(progress);
////                        downloadLength=sum;
////                    }
////                    fos.flush();
////                    //4.下载完成，安装apk
////                    installApk(getActivity(),file);
////                } catch (Exception e) {
////                    e.printStackTrace();
//////                    breakpoint(downloadUrl,emitter);
////                } finally {
////                    try {
////                        if (is != null)
////                            is.close();
////                        if (fos != null)
////                            fos.close();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        });
////
////    }
//
////   开始下载
//
//    private void startDownload(String downloadUrl) {
//        L.d("开始下下载");
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
//        //设置什么网络情况下可以下载
////        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        //设置通知栏的标题
//        request.setTitle("下载");
//        //设置通知栏的message
//        request.setDescription("StudyLive 正在下载....");
//        //设置漫游状态下是否可以下载
//        request.setAllowedOverRoaming(false);
//        File file = new File(Environment.DIRECTORY_DOWNLOADS + "/" + "StudyLive.apk");
//        System.out.println(file.getAbsolutePath());
//        //设置文件存放目录
//        request.setDestinationInExternalFilesDir(getContext(), Environment.DIRECTORY_DOWNLOADS, "StudyLive_update.apk");
//        //获取系统服务
//        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//        //进行下载,并取得下载id
//        final long id = downloadManager.enqueue(request);
//        //广播接收器
//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                System.out.println("接收广播,broadcastReceiver.onReceive");
//                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                //获取下载的id,若没有则返回-1(第二个参数)
//                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                //这里把传递的id和广播中获取的id进行对比是不是我们下载apk的那个id，如果是的话，就开始获取这个下载的路径
//                if (ID == id) {
//                    DownloadManager.Query query = new DownloadManager.Query();
//                    query.setFilterById(id);
//                    Cursor cursor = manager.query(query);
//                    if (cursor.moveToFirst()) {
//                        //获取文件下载路径
//                        String filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                        System.out.println("下载的文件路径:" + filePath);
//                        //如果文件名不为空,说明文件已存在,自动安装
//                        if (filePath != null) {
//                            Uri uri = manager.getUriForDownloadedFile(id);
//                            openAPK(filePath);
//                        }
//                    }
//                    cursor.close();
//                }
//            }
//        };
//        //动态注册广播,当下载完成后，下载管理会发出DownloadManager.ACTION_DOWNLOAD_COMPLETE这个广播，
//        // 并传递downloadId作为参数。通过接受广播我们可以打开对下载完成的内容进行操作
//        System.out.println("动态注册广播");
//        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//
//    }
//
//
//
//    /**
//     * 打开apk文件,安装
//     * @param filePath
//     */
//    private void openAPK(String filePath) {
//        System.out.println("openAPK,开始安装");
//        File file = new File(Uri.parse(filePath).getPath());
//        if (!file.exists()) {
//            System.out.println("下载的文件不存在!!!");
//            return;
//        }
//        System.out.println("要打开的文件:" + file.getAbsolutePath() + "是否存在:" + file.exists());
//        Intent intent = new Intent();
//        if (null != file) {
//            try {
//                //请求安装未知应用来源的权限
//                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 6666);
//                //兼容7.0/24以上
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    System.out.println("api24/sdk7.0以上");
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //第二个参数是跟manifest文件里的authorities属性是一致的,当包名修改的时候配置里的参数也要修改
//                    Uri uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileprovider",file);
//                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                    //兼容8.0/26以上,需要安装权限
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        System.out.println("api26/sdk8.0以上");
////                        ToastUtil.showMsg(context, "更新需要设置允许安装外部来源应用,将为您跳转设置界面");
//                        //这里不弄个定时器会下载完立刻打开设置界面,用户可能会有点懵逼
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                getPermission();
//                                if (!getContext().getPackageManager().canRequestPackageInstalls()) {
//                                    //这个是8.0新api
//                                    Intent intent1 = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    getContext().startActivity(intent1);
//                                    return;
//                                }
//                            }
//                        }, 6000);
//                    }
//                } else {
//                    System.out.println("api24/sdk7.0以下");
//                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                if (getContext().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
//                    getContext().startActivity(intent);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("打开安装界面");
//        getContext().startActivity(intent);
//    }


//    ////调用系统的安装方法
//    private void installAPK(File savedFile) {
//        //调用系统的安装方法
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri data;
//        // 判断版本大于等于7.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
//            data = FileProvider.getUriForFile(activity, Environment.DIRECTORY_DOWNLOADS, savedFile);
//            // 给目标应用一个临时授权
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            L.d("AutoUpdate","7.0data="+data);
//        } else {
//            data = Uri.fromFile(savedFile);
//            L.d("AutoUpdate","111data="+data);
//        }
//        intent.setDataAndType(data, "application/vnd.android.package-archive");
//        activity.startActivity(intent);
//        activity.finish();
//    }

}
