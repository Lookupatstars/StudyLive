package com.aaron.studylive.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aaron.studylive.R;
import com.aaron.studylive.activitys.DetailPlayerActivity;
import com.aaron.studylive.adapters.CpAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.CpData;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Loading;
import com.aaron.studylive.video.LandLayoutVideo;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Aaron on 2020/5/8
 * The current project is StudyLive
 *
 * @Describe:  章节信息界面  类似于课程页面
 *
 */

public class CpFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_cp_list)
    ListView mListView;

    LandLayoutVideo detail_player;
    DetailPlayerActivity detailPlayerActivity;

    private List<CpData> cpDataContent;  //存放章节数据的列表
    private CpAdapter mAdapter;
    private int mCourseId;
    private Loading mLoading;
    private int mCurrentPosition = 1;//当前播放视频的位置
    private int currentPos;//当前的listView的位置

    private boolean isPlay;
    private boolean isPause;
    OrientationUtils orientationUtils;

    private OnFragmentInteractionListener interactionListener;
    String result;

    private static final String homeUrl = "http://course-api.zzu.gdatacloud.com:890/";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

     @Override
     protected int getLayoutId() {
         return R.layout.fragment_cp;
     }

     @Override
     protected void init() {
         Bundle bundle = getArguments();
         if (bundle != null){
             mCourseId = bundle.getInt("CourseId");
         }

         cpDataContent = new ArrayList<>();
         mAdapter = new CpAdapter(getContext(), cpDataContent);
         mListView.setAdapter(mAdapter);
         mListView.setOnItemClickListener(this);

         new CpAsyncTask().execute();

         L.d("init 不知道执行了米有 mCourseId  = "+mCourseId);

     }

     /*
     重新解析课程数据，并存储到CPData中
      */
    private class CpAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getMediaInfo(mCourseId);
            L.d("CpFragment ->  CpAsyncTask + url = "+url);
            result = HttpRequest.getInstance().GET(url,null);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisCpJsonData(s);
        }
    }

    private void analysisCpJsonData(String s) {
        L.d("CpFragment ->  analysisCpJsonData + s = "+s);

        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("records");
                L.d("CpFragment ->  analysisCpJsonData + array.length = "+array.length());
                for (int i = 0; i< array.length(); i++){
                    CpData cpData = new CpData();
                    object = array.getJSONObject(i);

                    L.d("start:::: CpFragment ->  analysisCpJsonData  =  "+i);
                    L.d("请求到的播放链接的原始地址 =  "+object.getString("resourceAddress2"));
                    cpData.setCourseId(object.getInt("courseId"));
                    cpData.setCourseTime(object.getString("courseTime"));
                    cpData.setCreateTime(object.getString("createTime"));
                    cpData.setDownload(object.getInt("download"));
                    cpData.setDownloadCount(object.getInt("downloadCount"));
                    cpData.setId(object.getInt("id"));
                    cpData.setName(object.getString("name"));
                    cpData.setResourceAddress(homeUrl+object.getString("resourceAddress")); //下载资源地址
                    cpData.setResourceAddress2(homeUrl+object.getString("resourceAddress2"));  //播放资源地址
                    cpData.setStatus(object.getInt("status"));
                    cpData.setSummary(object.getString("summary"));
                    cpData.setUpdateTime(object.getString("updateTime"));
                    cpData.setUserId(object.getInt("userId"));
                    cpData.setViewCount(object.getInt("viewCount"));
                    cpData.setViewPermissions(object.getInt("viewPermissions"));

                    L.d("end:::: CpFragment ->  analysisCpJsonData ");
                    cpDataContent.add(cpData);
                }
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     @Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

         //当item被点击之后的处理，也就是。直接替换fragment
         mAdapter.notifyDataSetChanged();//刷新listView

         //侧边蓝点击之后，要修改直播课的FrameLayout的内容
         L.d("点击了第 "+position+"个？？？" + cpDataContent.get(position).getName());
         L.d("点击了第  "+position+" 个？？？" + cpDataContent.get(position).getResourceAddress2());

         String url = cpDataContent.get(position).getResourceAddress2();

         L.d("点击了第 "+ result);
//         this.detail_player = Objects.requireNonNull(getActivity()).findViewById(R.id.detail_player);
//         detailPlayerActivity.analysisJsonData(result,position);

         if (interactionListener != null) {
             interactionListener.onFragmentInteraction(position);
         }

     }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int s);
    }


}
