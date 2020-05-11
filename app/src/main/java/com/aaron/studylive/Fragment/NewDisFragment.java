package com.aaron.studylive.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Aaron on 2020/3/8
 * The current project is StudyLive
 *
 * @Describe:
 */
public class NewdisFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onRefresh() {
        // 延迟若干秒后启动刷新任务
        mHandler.postDelayed(mRefresh, 2000);
    }

    private Handler mHandler = new Handler(); // 声明一个处理器对象

    // 定义一个刷新任务
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            // 结束下拉刷新布局的刷新动作
//            srl_clothes.setRefreshing(false);
//            // 更新服装信息队列
//            for (int i = mAllArray.size() - 1, count = 0; count < 5; count++) {
//                GoodsInfo item = mAllArray.get(i);
//                mAllArray.remove(i);
//                mAllArray.add(0, item);
//            }
//            // 通知适配器的列表数据发生变化
//            mAdapter.notifyDataSetChanged();
//            // 让循环视图滚动到第一项所在的位置
//            rv_clothes.scrollToPosition(0);
        }
    };

}
