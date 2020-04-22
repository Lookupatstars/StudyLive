package com.aaron.studylive.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.activitys.ClassifyActivity;
import com.aaron.studylive.adapters.CourseListAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.CourseListData;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Loading;
import com.aaron.studylive.views.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class ClassFragment extends BaseFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener{

    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    //分类、扫描、搜索、最近学习
    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.iv_classify)
    ImageView mIvClassify;

    @Bind(R.id.iv_scan)
    ImageView mIvScan;

    @Bind(R.id.iv_study_latest)
    ImageView mIvStudyLatest;

    @Bind(R.id.refresh_listview_class)
    RefreshListView mRefreshListView;


    private CourseListAdapter mAdapter;

    private List<CourseListData> mCourseDatas;

    private Loading mLoading;

    private int mCurrentPage = 1;//当前页面

    private boolean mIsRefshing = false;//是否正在刷新

    private boolean mIsLoadingMore = false;//是否正在加载更多

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class;
    }

    @Override
    protected void init() {
        initView();
        setupClick();

        new CourseListNewAsyncTask().execute();
    }

    private void initView(){
        mCourseDatas = new ArrayList<>();
        mAdapter = new CourseListAdapter(getActivity(),mCourseDatas);
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
        mIvClassify.setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        mIvStudyLatest.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_classify:
                toast("点击了分类信息");
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                break;
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
                toast("点击了扫描信息");
                break;
            case R.id.iv_search:
                toast("点击了搜索信息");

                break;
            case R.id.iv_study_latest:
                toast("点击了最近学习信息");

                break;
        }
    }


    /**
     * 解析课程列表数据
     * @param s
     */
    private void analysisCourseListNewJsonData(String s) {
        L.d("analysisCourseListJsonData::SSS"+s);
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            mRefreshListView.refreshComplete();

            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("list");
                L.d("+2::"+array);
                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    L.d("analysisCourseListJsonData::"+i);
                    L.d("id::"+object.getInt("id"));
                    L.d("summary:::"+object.getString("summary"));
                    L.d("view_count::"+object.getInt("view_count"));
                    L.d("name:::"+object.getString("name"));
                    L.d("update_time::"+object.getString("update_time"));
                    L.d("type:::"+object.getInt("type"));
                    L.d("pic::"+object.getString("img"));

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setDesc(object.getString("summary"));
                    data.setNumbers(object.getInt("view_count"));
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
        new CourseListNewAsyncTask().execute();
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadingMore) {
            mCurrentPage++; //可以通过currentPage来控制刷新多少页？？
            mIsLoadingMore = true;
            new CourseListNewAsyncTask().execute();
        }
    }

    //点击之后打开视频播放界面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        toast("点击了视频，播放暂未实现");
//        CourseListData data = mCourseDatas.get(position-2);
//        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
//        intent.putExtra("id", data.getId());
//        intent.putExtra("title", data.getName());
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }

    private class CourseListNewAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String url = HttpUrl.getInstance().getCourseListUrlNew();
            Map<String, String> params = HttpUrl.getInstance().getCourseListNewParams(mCurrentPage);
            String result = HttpRequest.getInstance().POST(url, params);
            L.d("CourseListAsyncTask"+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisCourseListNewJsonData(s);
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


    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

}
