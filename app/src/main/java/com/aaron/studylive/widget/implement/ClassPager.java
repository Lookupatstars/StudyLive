package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.ClassMenu;
import com.aaron.studylive.constant.AppContants;
import com.aaron.studylive.utils.CacheUtils;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.widget.BasePager;
import com.aaron.studylive.widget.implement.menu.TabDetailPager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 直播课程
 */
public class ClassPager extends BasePager {

    private static final String TAG = "ClassPager";
    private ClassMenu classMenu;
    private ViewPager mViewPager;
    private TextView view;

    public ClassPager(Activity activity) {
        super(activity);
    }

    //类似  Homepager 一样
    @Override
    public void initData() {
        L.d("initData()   执行了 ");
        //在请求服务器之前，先判断有没有缓存。有的话，先加载缓存
        String cache = CacheUtils.getCache(AppContants.COURSE_WEB,mActivity);
        if (!TextUtils.isEmpty(cache)){
            L.d("发现缓存,直接解析数据");
            processData(cache);
        }
        L.d("有没有缓存都要重新请求数据库");
        //请求服务器，获取数据
        //自己搭建的HttpUtils
        getDataFromServer();

        //初始化页签class tab menu
        for (int i = 0;i<classMenu.content.size();i++){
            L.d("data.content.get(i):::"+classMenu.content.get(i));

            //要给帧布局填充一个布局。数据
            view = new TextView(mActivity); //mActivity 是来自基类的对象
            view.setText(classMenu.content.get(i).name); //在这里初始化会空指针
            view.setTextColor(Color.RED);
            view.setTextSize(22);
            view.setGravity(Gravity.CENTER);
            L.d("classMenu.content.get(i).name："+classMenu.content.get(i).name);
            mViewPager.addView(view);
        }
        mViewPager.setAdapter( new ClassMenuDetailAdapter());
//        L.d("classMene：："+classMenu);
    }
    //一定是initView先执行
    @Override
    public View initView() {
        L.d("initView() 执行了");
        View view = View.inflate(mActivity, R.layout.pager_class_menu_detail,null);
        mViewPager = view.findViewById(R.id.vp_class_menu_detail);
        return super.initView();
    }

    //给ViewPager填充界面 ，需要一个Adapter类
    class ClassMenuDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            L.d("tabDetailPagers.size::"+classMenu.content.size());
            return classMenu.content.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ClassPager pager = new ClassPager(mActivity);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }





    //获取网络数据 CourseWeb
    private void getDataFromServer() {
        L.d("getDataFromServer 执行了");

        final String address = AppContants.COURSE_WEB;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean hasError = false;
                String result = null;

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");  //POST 方式都要大写
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //response 就是result
                    L.d("查看responses的结果：：" + response);
                    processData(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
                //END try catch finally
        }
        }).start();

        L.d("getDataFromServer  结束了");

    }

    //Gson 解析数据 CourseWeb
    private void processData(String json) {
        Gson gson = new Gson();
        ClassMenu data = gson.fromJson(json, ClassMenu.class);  //classMenu里边已经是解析的结果
        this.classMenu = data;
    }
}
