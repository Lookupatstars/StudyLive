package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.MainActivity;
import com.aaron.studylive.MainActivityWithTab;
import com.aaron.studylive.bean.ClassMenu;
import com.aaron.studylive.constant.AppContants;
import com.aaron.studylive.utils.CacheUtils;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.widget.BasePager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 直播课程
 */
public class ClassPager extends BasePager {
    public ClassPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //要给帧布局填充一个布局。数据
        TextView view = new TextView(mActivity); //mActivity 是来自基类的对象
        view.setText("直播课程");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        //用基类的fl_content添加布局
        fl_content.addView(view);

        //在请求服务器之前，先判断有没有缓存。有的话，先加载缓存
        String cache = CacheUtils.getCache(AppContants.COURSE_WEB,mActivity);
        if (!TextUtils.isEmpty(cache)){
            L.d("发现缓存,直接解析数据");
            processData(cache);
        }
        //请求服务器，获取数据
        //使用第三方开源框架XUtils
        getDataFromServer();

    }

    //获取网络数据 CourseWeb
    private void getDataFromServer() {
        RequestParams params = new RequestParams(AppContants.COURSE_WEB);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {

            private boolean hasError = false;
            private String result = null;
            @Override
            public void onSuccess(String result) {
                //成功
//                String res = result.toString();
//                L.d("服务器返回结果："+res);
                //如果服务返回304或onCache选择了信任缓存,这时result为null
                Log.i("JAVA", "开始请求");
                if (result != null) {
                    this.result = result;
                    L.d("服务器但会结果：："+result);
                    //Gson 解析数据
                    processData(result);

                    //把数据写入缓存
                    CacheUtils.setCache(AppContants.COURSE_WEB,result,mActivity);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { //网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    //...
                } else { //其他错误
                    //...
                    L.d("其他错误！");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //Gson 解析数据 CourseWeb
    private void processData(String json) {
        Gson gson = new Gson();
        ClassMenu classMenu = gson.fromJson(json, ClassMenu.class);  //homeMune里边已经是解析的结果
        L.d("Gson解析的结果：："+classMenu);


        //获取侧边栏对象
        MainActivityWithTab mainUI = (MainActivityWithTab) mActivity;



    }

}
