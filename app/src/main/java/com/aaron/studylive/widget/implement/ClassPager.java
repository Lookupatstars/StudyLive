package com.aaron.studylive.widget.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 直播课程
 */
public class ClassPager extends BasePager {

    private ArrayList<TabDetailPager> tabDetailPagers; //详情页的集合
    private ClassMenu classMenu;

    private ViewPager mViewPager;

    public ClassPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_class_menu_detail,null);
        mViewPager = view.findViewById(R.id.vp_class_menu_detail);
        return super.initView();
    }

    @Override
    public void initData() {
        tabDetailPagers = new ArrayList<TabDetailPager>();//初始化tab标签的ArrayList

        //在请求服务器之前，先判断有没有缓存。有的话，先加载缓存
        String cache = CacheUtils.getCache(AppContants.COURSE_WEB,mActivity);
        if (!TextUtils.isEmpty(cache)){
            L.d("发现缓存,直接解析数据");
            processData(cache);
        }
        L.d("有没有缓存都要重新请求数据库");
        //请求服务器，获取数据
        //使用第三方开源框架XUtils
        getDataFromServer();

        //初始化页签class tab menu
        for (int i = 0;i<classMenu.content.size();i++){
            L.d("classMenu.content.get(i):::"+classMenu.content.get(i));
            TabDetailPager pager = new TabDetailPager(mActivity,classMenu.content.get(i));
            tabDetailPagers.add(pager);
        }

        mViewPager.setAdapter( new ClassMenuDetailAdapter());
        L.d("mViewPager.setAdapter( new ClassMenuDetailAdapter());执行了");
    }


    //给ViewPager填充界面 ，需要一个Adapter类
    class ClassMenuDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager pager = tabDetailPagers.get(position);
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
        RequestParams params = new RequestParams(AppContants.COURSE_WEB);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {

            private boolean hasError = false;
            private String result = null;
            @Override
            public void onSuccess(String result) {
                //成功
//                String res = result.toString();
                //如果服务返回304或onCache选择了信任缓存,这时result为null
                L.d("JAVA, 开始请求");
                if (result != null) {
                    this.result = result;
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

        L.d("getDataFromServer  结束了");

    }

    //Gson 解析数据 CourseWeb
    private void processData(String json) {
        Gson gson = new Gson();
        classMenu = gson.fromJson(json, ClassMenu.class);  //classMenu里边已经是解析的结果
        L.d("Gson解析的结果：："+classMenu);

//        classMenu.content.get(i).name;   //是每个标签的名字



        //获取侧边栏对象
//        MainActivityWithTab mainUI = (MainActivityWithTab) mActivity;
//        LeftMenuFragment fragment = mainUI.getLeftMenuFragment();  //获取侧边栏

        //给侧边栏设置数据--让Fragment更新界面
//        fragment.setMenuData(classMenu.content);

        //设置详情页
//        mMenuDetailPager = new ArrayList<BaseMenuDetailPager>();
//        mMenuDetailPager.add(new CourseWebDetailPager(mActivity,classMenu.content.get(0).name)); // 课程的列表

        //默认显示第一个详情页
//        setCurrentDetailPager(0);
    }

    //设置直播课程详情页
//    public void setCurrentDetailPager( int position){
        //重新给Frame Layout添加内容
//        BaseMenuDetailPager pager = mMenuDetailPager.get(position); //获取应该显示的界面
//        View view = pager.mRootView; // 当前页面

        //清除之前的布局
//        fl_content.removeAllViews();
//        fl_content.addView(view); //给帧布局添加布局

        //初始化页面数据
//        pager.initData();

        //更新标题
//        tv_title.setText(classMenu.content.get(position).type);
//    }

}
