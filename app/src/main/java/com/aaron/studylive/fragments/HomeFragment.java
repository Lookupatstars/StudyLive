package com.aaron.studylive.fragments;


import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.adapter.LiveClassAdapter;
import com.aaron.studylive.adapters.CourseListAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.BannerData;
import com.aaron.studylive.bean.CourseListData;
import com.aaron.studylive.bean.LiveClassInfo;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.CacheUtils;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Loading;
import com.aaron.studylive.views.FlyBanner;
import com.aaron.studylive.views.RefreshListView;
import com.aaron.studylive.widget.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 使用FlyBanner作为轮播图
 *
 * 这个界面最后可能还需要加上 我也是有底线的~~~
 *
 * 或者可以加上教师的信息列表？？
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener
        , RefreshListView.OnRefreshListener , AdapterView.OnItemClickListener {

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.refresh_listview)
    RefreshListView mRefreshListView;

    private FlyBanner mBanner;
    private List<CourseListData> mCourseDatas;
    private List<BannerData> mBannerDatas;
    private CourseListAdapter mAdapter;
    private Loading mLoading;
    private View mHeaderView;  // 头部fragment_home_header.xml的初始化
    private int mCurrentPage = 1;//当前页面
    private boolean mIsRefshing = false;//是否正在刷新
    private boolean mIsLoadingMore = false;//是否正在加载更多
    private RecyclerView rv_hor_live_class;


    //获取到activity
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initView();
        setupClick();
        new BannerAsyncTask().execute();
        setBanner();
        new CourseListHotAsyncTask().execute();
    }


    // 头部 fragment_home_header.xml 的 banner、课程列表 初始化
    private void initView(){
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
        mRefreshListView.addHeaderView(mHeaderView);
        mBanner = ButterKnife.findById(mHeaderView,R.id.fb_banner);

        //初始化直播公开课
        // 从布局文件中获取循环视图
        rv_hor_live_class = mHeaderView.findViewById(R.id.rv_hor_live_class);
        // 创建一个横向的瀑布流布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);
        // 设置循环视图的布局管理器
        rv_hor_live_class.setLayoutManager(manager);
        // 构建一个直播公开课信息列表的瀑布流适配器
        LiveClassAdapter liveClassAdapter = new LiveClassAdapter(getActivity(), LiveClassInfo.getDefaultStag());
        // 设置瀑布流列表的点击监听器
        liveClassAdapter.setOnItemClickListener(liveClassAdapter);
        // 给rv_hor_live_class设置直播公开课信息瀑布流适配器
        rv_hor_live_class.setAdapter(liveClassAdapter);
        // 设置rv_hor_live_class的默认动画效果
        rv_hor_live_class.setItemAnimator(new DefaultItemAnimator());
        // 给rv_hor_live_class添加列表项之间的空白装饰
        rv_hor_live_class.addItemDecoration(new SpacesItemDecoration(1));

        mCourseDatas = new ArrayList<>();
        mAdapter = new CourseListAdapter(getActivity(), mCourseDatas);
        mRefreshListView.setAdapter(mAdapter);
        mRefreshListView.setOnRefreshListener(this);
        mRefreshListView.setOnItemClickListener(this);
        mLoading = Loading.getInstance(getActivity());
        showLoading();

    }

    /**
     * 设置点击事件
     */
    private void setupClick() {
        mIvSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_classify:
//                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
//                startActivity(intent1);
//                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
//                break;
//            case R.id.tab_one:
//                Intent intent2 = new Intent(getActivity(), JobLineActivity.class);
//                startActivity(intent2);
//                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
//                break;
//            case R.id.tab_two:
//                Intent intent3 = new Intent(getActivity(), RaiseActivity.class);
//                startActivity(intent3);
//                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
//                break;
            case R.id.iv_scan:

                break;
            case R.id.iv_search:
                toast("点击了搜索");

                break;
            case R.id.iv_study_latest:

                break;
        }
    }

    /**
     * 解析广告栏数据
     * @param //s
     */
    private void analysisBannnerJsonData(String s) {
        L.d("analysisBannnerJsonData::SSS"+s);
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            if (errorCode == 0) {
                mBannerDatas = new ArrayList<>();
                JSONArray array =object.getJSONObject("content").getJSONArray("list");
                L.d("+2::"+array);
                for (int i = 0; i < array.length(); i++) {
                    BannerData data = new BannerData();
                    object = array.getJSONObject(i);

                    L.d("name:::"+object.getString("name"));
                    L.d("img::"+object.getString("img"));
                    //每当数据不对的时候，总是会停止，

                    data.setName(object.getString("name"));
//                    data.setPic(object.getString("img"));
//                    debug(data.toString());
                    mBannerDatas.add(data);
                }
                setBanner();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置广告栏
     */
    private void setBanner() {
//        List<String> imgs = new ArrayList<>();
//        for (BannerData data : mBannerDatas) {
//            imgs.add(data.getPic());  //??????????????????
//        }
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.banner1);
        imgs.add(R.drawable.banner2);
        imgs.add(R.drawable.banner3);
        imgs.add(R.drawable.banner4);
        imgs.add(R.drawable.banner_5);
        //设置的网络图片
        mBanner.setImages(imgs);
        mBanner.setPointsIsVisible(true);
        mBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }


    /**
     * 解析课程列表数据
     * @param s
     */
    private void analysisCourseListHotJsonData(String s) {
        L.d("analysisCourseListJsonData::SSS"+s);
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            mRefreshListView.refreshComplete();

            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("records");
                L.d("+2::"+array);
                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setDesc(object.getString("summary"));
                    data.setNumbers(object.getInt("viewCount"));
//                    data.setUpdateTime(object.getLong("update_time"));
                    data.setCoursetype(object.getInt("type"));
                    data.setPic(object.getString("img"));

//                    data.setLastTime(object.getLong("last_time"));
//                    data.setChapterSeq(object.getInt("chapter_seq"));
//                    data.setMediaSeq(object.getInt("media_seq"));

//                    debug(data.toString());

                    mCourseDatas.add(data);
                }
                hideLoading();
                mAdapter.notifyDataSetChanged();

                if (mIsRefshing == true) {
                    toast("刷新成功");
                }
                mIsRefshing = false;
                mIsLoadingMore = false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            mRefreshListView.refreshComplete();
        }
    }


    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mIsRefshing = true;
        mCourseDatas.clear();
        new CourseListHotAsyncTask().execute();
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadingMore) {
            mCurrentPage++;
            mIsLoadingMore = true;
            new CourseListHotAsyncTask().execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        toast("点击了视频，但是播放暂未实现");
//        CourseListData data = mCourseDatas.get(position-2);
//        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
//        intent.putExtra("id", data.getId());
//        intent.putExtra("title", data.getName());
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }

    private class BannerAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            //在请求服务器之前，先判断有没有缓存。有的话，先加载缓存
            String cache = CacheUtils.getCache(HttpUrl.getInstance().getBannerUrl(),getActivity());
            if (!TextUtils.isEmpty(cache)){
                L.d("发现缓存,直接解析数据analysisBannnerJsonData");
//                analysisBannnerJsonData(cache);
            }
            L.d("有没有缓存都要重新请求数据库analysisBannnerJsonData");

            String url = HttpUrl.getInstance().getBannerUrl();
            Map<String, String> params = HttpUrl.getInstance().getBannerParams();
            String result = HttpRequest.getInstance().POST(url, params);
            L.d("BannerAsyncTask:result"+result);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            analysisBannnerJsonData(s);
        }
    }

    private class CourseListHotAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String cache = CacheUtils.getCache(HttpUrl.getInstance().getCourseListUrlHot(),getActivity());
            if (!TextUtils.isEmpty(cache)){
                L.d("发现缓存,直接解析数据analysisCourseListHotJsonData");
                analysisCourseListHotJsonData(cache);
            }
            L.d("有没有缓存都要重新请求数据库analysisCourseListHotJsonData");

            String url = HttpUrl.getInstance().getCourseListUrlNew(mCurrentPage);
            Map<String, String> params = HttpUrl.getInstance().getCourseListHotParams(mCurrentPage);
            String result = HttpRequest.getInstance().GET(url, null);
            L.d("url = "+url);
            L.d("getCourseListHotParams,result =  "+result);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisCourseListHotJsonData(s);
        }
    }


    private void showLoading() {
        mLoading.show();
        mRefreshListView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mLoading.hide();
        mRefreshListView.setVisibility(View.VISIBLE);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private void debug(String str) {
        Log.d(CourseFragment.class.getSimpleName(), str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(getActivity());
    }
}
