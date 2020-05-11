package com.aaron.studylive.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
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

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Aaron on 2020/5/8
 * The current project is StudyLive
 *
 * @Describe:  章节介绍信息界面
 *
 */
public class CourseIntroFragment extends BaseFragment implements RefreshListView.OnRefreshListener ,
        AdapterView.OnItemClickListener {

    @Bind(R.id.rfl_listview)
    RefreshListView mListView;

    private List<CourseListData> listDatas = new ArrayList<>();
    private CourseListAdapter mAdapter;

    private View mHeaderView;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvIntro;


    private String mTitle;  //课程名
    private String summary; //课程简介
    private int classType;

    private int mCurrentPage = 1;//当前页面
    private Loading mLoading;
    private boolean mIsRefshing = false;//是否正在刷新
    private boolean mIsLoadingMore = false;//是否正在加载更多

    private static final String ImgUrl = "http://course-api.zzu.gdatacloud.com:890/";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_intro;
    }

    @Override
    protected void init() {

        Bundle bundle = getArguments();
        if (bundle != null){
            mTitle = bundle.getString("title");
            summary = bundle.getString("summary");
            classType = bundle.getInt("type");
        }

        setupHeaderView();
        setIntroContent();

        listDatas.clear();

        mAdapter = new CourseListAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        //关闭view的OverScroll
        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mLoading = Loading.getInstance(getActivity());
        showLoading();

        new CourseListHotAsyncTask().execute();
    }

    private void setupHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_intro_header, null);

        mTvTitle = ButterKnife.findById(mHeaderView, R.id.tv_title); //课程简介的课程名
        mTvContent = ButterKnife.findById(mHeaderView, R.id.tv_summary);    //课程简介
        mTvIntro = ButterKnife.findById(mHeaderView, R.id.tv_intro);  //课程须知

        mListView.addHeaderView(mHeaderView);
    }

    private void setIntroContent(){
        mTvTitle.setText(mTitle);
        mTvContent.setText(summary);
        mTvIntro.setText("等待老师添加！");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class CourseListHotAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                String url = HttpUrl.getInstance().getRelevantCourse();
                String result = HttpRequest.getInstance().GET(url, null);
                return result;
            } catch (Exception e){
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisCourseListHotJsonData(s);
        }
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
            mListView.refreshComplete();

            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("records");
                L.d("+2::"+array);
                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    if (object.getInt("type") == classType && !object.getString("name").equals(mTitle)){

                        data.setId(object.getInt("id"));
                        data.setName(object.getString("name"));
                        data.setDesc(object.getString("summary"));
                        data.setNumbers(object.getInt("viewCount"));
                        data.setPic(ImgUrl + object.getString("img"));
                        data.setThumb(ImgUrl + object.getString("img2"));
                        data.setNumLession(object.getInt("lessonNum"));
                        data.setClassType(object.getInt("type"));

                        L.d("Home    name:::"+object.getString("name"));
                        L.d("Home     lessonNum::"+ object.getInt("lessonNum"));
                        L.d("Home     setClassType::"+ object.getInt("type"));

                        listDatas.add(data);
                    } //End if
                } //End for

                hideLoading();
                mAdapter.notifyDataSetChanged();

                if (mIsRefshing == true) {
                    Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                }
                mIsRefshing = false;
                mIsLoadingMore = false;

            }


        } catch (JSONException e) {
            e.printStackTrace();
            mListView.refreshComplete();
        }
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mIsRefshing = true;
        listDatas.clear();
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


    private void showLoading() {
        mLoading.show();
        mListView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mLoading.hide();
        mListView.setVisibility(View.VISIBLE);
    }


}
