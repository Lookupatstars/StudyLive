package com.aaron.studylive.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aaron.studylive.R;
import com.aaron.studylive.adapters.CourseListAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.BannerData;
import com.aaron.studylive.bean.CourseListData;
import com.aaron.studylive.utils.Loading;
import com.aaron.studylive.views.FlyBanner;

import java.util.List;


public class ClassFragment extends BaseFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    //分类、扫描、搜索、最近学习
    private ImageView mIvClassify, mIvScan,mIvSearch,mIvStudyLatest;
    private CourseListAdapter mAdapter;

    private List<CourseListData> mCourseDatas;

    private List<BannerData> mBannerDatas;

    private View mHeaderView;

    private FlyBanner mBanner;

    private LinearLayout mTabOne;//求职路线计划

    private LinearLayout mTabTwo;//加薪利器计划

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

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
