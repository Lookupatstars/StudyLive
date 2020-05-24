package com.aaron.studylive.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aaron.studylive.R;
import com.aaron.studylive.adapters.CpAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.CpData;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Bind(R.id.ll_cp_height)
    LinearLayout ll_cp_height;

    private List<CpData> cpDataContent;  //存放章节数据的列表
    private CpAdapter mAdapter;
    private int mCourseId;
    private int h=1;
//    private CourseCommentFragment mCommentFragmet = new CourseCommentFragment();
    private OnFragmentInteractionListener interactionListener;
    String result;
    private int lessonid;

    private static final String homeUrl = "http://course-api.zzu.gdatacloud.com:890/";

    public void setLessonid(int lessonid) {
        this.lessonid = lessonid;
    }

    public int getLessonid() {
        return lessonid;
    }

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
//             lessionNum = bundle.getInt("lessionNum");
         }

         cpDataContent = new ArrayList<>();
         mAdapter = new CpAdapter(getContext(), cpDataContent);
         mListView.setAdapter(mAdapter);
         mListView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
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
            result = HttpRequest.getInstance().GET(getContext(),url,null);
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
                    h = array.length();
                    L.d("start:::: CpFragment ->  analysisCpJsonData  =  "+i);
                    L.d("cpData  的课程Id 是多少 =  "+object.getInt("id"));
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
                    if (i == 0) {
                        cpData.setSeleted(true);
                    }
                    cpDataContent.add(cpData);
                }
                setHeight();
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
         clearListSelected();
         cpDataContent.get(0).setSeletedEnd(true);
         setLessonid(cpDataContent.get(position).getId());
         //侧边蓝点击之后，要修改直播课的FrameLayout的内容
         L.d("点击了第 "+position+"个   id 是啊？？？" + cpDataContent.get(position).getId());
         L.d("点击了第  "+position+" 个？？？" + cpDataContent.get(position).getResourceAddress2());
//         SendValue(position);
         String url = cpDataContent.get(position).getResourceAddress2();
         cpDataContent.get(position).setSeleted(true);
         cpDataContent.get(position).setSeletedEnd(true);
         L.d("点击了第 "+ result);

         if (interactionListener != null) {
             interactionListener.onFragmentInteraction(position);
         }

     }

    //传递lesson的id给评论，
//    private void SendValue(int i){
//        Bundle bundle1 = new Bundle();
//        L.d("  id 是 " + cpDataContent.get(i).getId());
//        bundle1.putInt("lessonId",cpDataContent.get(i).getId());
//        mCommentFragmet.setArguments(bundle1);
//    }


    private void setHeight(){
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mListView.getLayoutParams();

        params.weight=LinearLayout.LayoutParams.MATCH_PARENT;
        L.d("高度  cp "+(50*h));
        params.height= Utils.dip2px(Objects.requireNonNull(getActivity()),50)*h;

        mListView.setLayoutParams(params);
    }

    private void clearListSelected() {
        for (CpData data : cpDataContent)
            data.setSeleted(false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int s);
    }


}
