package com.aaron.studylive.fragments;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.aaron.studylive.R;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.views.RefreshListView;

import butterknife.Bind;

public class HomeFragment extends BaseFragment implements View.OnClickListener
        , RefreshListView.OnRefreshListener , AdapterView.OnItemClickListener {

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.refresh_listview)
    RefreshListView mRefreshListView;

    private View mHeaderView;  // 头部fragment_home_header.xml的初始化


    //获取到activity
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initView();


    }

    // 头部 fragment_home_header.xml 的 banner、课程列表 初始化
    private void initView(){
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
        mRefreshListView.addHeaderView(mHeaderView);
        
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
