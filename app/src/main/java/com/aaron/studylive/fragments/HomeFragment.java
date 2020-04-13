package com.aaron.studylive.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.activitys.CoursePlayActivity;
import com.aaron.studylive.adapters.CourseListAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.BannerData;
import com.aaron.studylive.bean.CourseListData;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Loading;
import com.aaron.studylive.views.FlyBanner;
import com.aaron.studylive.views.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        new CourseListAsyncTask().execute();

    }

    // 头部 fragment_home_header.xml 的 banner、课程列表 初始化
    private void initView(){
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
        mRefreshListView.addHeaderView(mHeaderView);
        mBanner = ButterKnife.findById(mHeaderView,R.id.fb_banner);

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
                    data.setPic(object.getString("img"));
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
        List<String> imgs = new ArrayList<>();
        for (BannerData data : mBannerDatas) {
            imgs.add(data.getPic());  //??????????????????
        }
        //设置的网络图片
        mBanner.setImagesUrl(imgs);
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
    private void analysisCourseListJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            mRefreshListView.refreshComplete();

            if (errorCode == 0) {
                JSONArray array = object.getJSONArray("content");
                L.d("analysisCourseListJsonData:array"+array);
                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setDesc(object.getString("desc"));
                    data.setIsLearned(object.getInt("is_learned"));
                    data.setCompanyId(object.getInt("company_id"));
                    data.setNumbers(object.getInt("numbers"));
                    data.setUpdateTime(object.getLong("update_time"));
                    data.setCoursetype(object.getInt("coursetype"));
                    data.setDuration(object.getLong("duration"));
                    data.setFinished(object.getInt("finished"));
                    data.setIsFollow(object.getInt("is_follow"));
                    data.setMaxChapterSeq(object.getInt("max_chapter_seq"));
                    data.setMaxMediaSeq(object.getInt("max_media_seq"));
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
        new CourseListAsyncTask().execute();
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadingMore) {
            mCurrentPage++;
            mIsLoadingMore = true;
            new CourseListAsyncTask().execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseListData data = mCourseDatas.get(position-2);
        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
        intent.putExtra("id", data.getId());
        intent.putExtra("title", data.getName());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }

    private class BannerAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getBannerUrl();
            Map<String, String> params = HttpUrl.getInstance().getBannerParams();
            String result = HttpRequest.getInstance().POST(url, params);
            L.d("BannerAsyncTask:result"+result);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisBannnerJsonData(s);
        }
    }

    private class CourseListAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String url = HttpUrl.getInstance().getCourseListUrl();
            Map<String, String> params = HttpUrl.getInstance().getCourseListParams(mCurrentPage);
            String result = HttpRequest.getInstance().POST(url, params);
            L.d("CourseListAsyncTask"+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisCourseListJsonData(s);
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


}
