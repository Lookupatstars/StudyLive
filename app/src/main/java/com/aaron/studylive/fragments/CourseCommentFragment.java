package com.aaron.studylive.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.adapters.CourseCommentAdapter;
import com.aaron.studylive.base.BaseFragment;
import com.aaron.studylive.bean.ClassInCommentData;
import com.aaron.studylive.bean.CommentContent;
import com.aaron.studylive.bean.LoginData;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aaron on 2020/5/8
 * The current project is StudyLive
 *
 * @Describe:  课程评论
 *
 */
public class CourseCommentFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.ll_comment_height)
    LinearLayout ll_comment_height;

    @Bind(R.id.comment_list)
    ListView mListView;

    @Bind(R.id.tv_no_comment)
    TextView tv_no_comment;

    @Bind(R.id.et_send_comment)
    TextView et_send_comment;

    @Bind(R.id.btn_up_comment)
    Button btn_up_comment;

    private List<ClassInCommentData> classInCommentData = new ArrayList<>();
    private List<CommentContent> contentList = new ArrayList<>();
    private int mCourseId;
    LoginData LoginData = new LoginData();
//    private CommentExpandAdapter adapter;
    private CourseCommentAdapter mAdapter;
    private CpFragment cpFragment = new CpFragment();
    private int mPage = 1;
    private int lessonId;
    private int h=1;
    private BottomSheetDialog dialog;
    private static final String homeUrl = "http://course-api.zzu.gdatacloud.com:890/";

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_comment;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        if (bundle != null){
            mCourseId = bundle.getInt("CourseId");
        }
        new ClassInCommentAsyncTask().execute();



//        initExpandableListView(listDatas);
        et_send_comment.setOnClickListener(this);
        mListView.setOnItemClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //评论点击
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.et_send_comment){
            showCommentDialog();
        }
    }

    //回复点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = ((TextView) view.findViewById(R.id.tv_comment_name)).getText().toString();
//        L.d("回复 " + contentRecords.get(position).name + " 的评论:");
//        L.d("回复 给课程 id 为 " + contentRecords.get(position).courseId + " 的评论:");
//        L.d("回复 给ID  " + contentRecords.get(position).id + " 的评论:");
//        L.d("回复 给 lesson ID  " + contentRecords.get(position).lessonId + " 的评论:");
        showReplyDialog(position);
    }


    //所有课程
    private class ClassInCommentAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getMediaInfo(mCourseId);
            L.d("CpFragment ->  CpAsyncTask + url = "+url);
            return HttpRequest.getInstance().GET(getContext(),url,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisClassInCommentJsonData(s);
        }
    }

    //解析课程列表
    private void analysisClassInCommentJsonData(String s) {
        L.d("analysisClassInCommentJsonData   = "+s);
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("records");

                L.d("第一节课的id是多少 = "+array.getJSONObject(0).getInt("id"));
                lessonId = array.getJSONObject(0).getInt("id");
                new DisplayCommentAsyncTask().execute();

//                for (int i = 0; i< array.length(); i++){
//                    ClassInCommentData cpData = new ClassInCommentData();
//                    object = array.getJSONObject(i);
//
//                    cpData.setCourseId(object.getInt("courseId"));
//                    cpData.setCourseTime(object.getString("courseTime"));
//                    cpData.setCreateTime(object.getString("createTime"));
//                    cpData.setDownload(object.getInt("download"));
//                    cpData.setDownloadCount(object.getInt("downloadCount"));
//                    cpData.setId(object.getInt("id"));
//                    cpData.setName(object.getString("name"));
//                    cpData.setResourceAddress(homeUrl+object.getString("resourceAddress")); //下载资源地址
//                    cpData.setResourceAddress2(homeUrl+object.getString("resourceAddress2"));  //播放资源地址
//                    cpData.setStatus(object.getInt("status"));
//                    cpData.setSummary(object.getString("summary"));
//                    cpData.setUpdateTime(object.getString("updateTime"));
//                    cpData.setUserId(object.getInt("userId"));
//                    cpData.setViewCount(object.getInt("viewCount"));
//                    cpData.setViewPermissions(object.getInt("viewPermissions"));
//
//                    classInCommentData.add(cpData);
//                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //end  所有课程

    //只查询本门课程的第一节课的评论，只需要把所有的评论都集中在第一节课就可以了
    private class DisplayCommentAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getCommentUrl(lessonId);
            L.d("请求评论列表的  url = "+url);
            String str =  HttpRequest.getInstance().GET(getContext(),url, null);
            return str;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisDisplayCommentJsonData(s);
        }
    }

//    //原始请求评论数据
//    public void getData(){
//        SharedPreferences share =  getContext().getSharedPreferences("Session",MODE_PRIVATE);
//        String sessionid= share.getString("sessionid","null");
//
//        OKHttpEntity.GetRequest(HttpUrl.getInstance().getCommentUrl(lessonId),new MyCallback(),sessionid);
//    }
//
//    private class MyCallback implements Callback {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            L.d("网络出错了！！！");
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            ResponseBody requestBody = response.body();
//            final String result = requestBody.toString();
//            analysisDisplayCommentJsonData(result);
//        }
//    }

    //解析评论列表
    private void analysisDisplayCommentJsonData(String json) {

        try {
            JSONObject object = new JSONObject(json).getJSONObject("content");
            L.d("________________________________________ object = "+object);
            Gson gson = new Gson();
            CommentContent data = gson.fromJson(object.toString(), CommentContent.class);
            L.d("________________________________________ data = "+data);

            L.d("看看有多少个回复 = "+h);
            contentList.add(data);
            h = contentList.size();
            L.d("第一次设置高度 h 的个数  analysisDisplayCommentJsonData = "+h);

            L.d("contentList = "+contentList);

            if (contentList.get(0).total == 0){
                tv_no_comment.setVisibility(View.VISIBLE);
            }
            mAdapter = new CourseCommentAdapter(getActivity(), contentList);
            mListView.setAdapter(mAdapter);
            mListView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

            mAdapter.notifyDataSetChanged();

            setHeight();
            setListViewHeight(mListView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //评论回复
    private void SendCommentWithOkHttp(String content ,int courseId,int lessonId,int pid,int type ) {
        new Thread(new Runnable() {
            public void run() {
                JSONObject object = new JSONObject();

                try {
                    object.put("content",content);
                    object.put("courseId",courseId);
                    object.put("lessonId",lessonId);
                    object.put("pid", pid);
                    object.put("type", type);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                MediaType type = MediaType.parse("application/json;charset=UTF-8");
                RequestBody requestBody = RequestBody.create(type, "" + object.toString());

                try {
                    SharedPreferences share =  getContext().getSharedPreferences("Session",MODE_PRIVATE);
                    String sessionid= share.getString("sessionid","null");

                    String url = HttpUrl.getInstance().getSendComment(lessonId);
                    L.d("url = "+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("cookie",sessionid)
                            // 指定访问的服务器地址
                            .url(url).post(requestBody)
                            .build();
                    L.d("2");
                    Response response = client.newCall(request).execute();
                    L.d("3");
                    String responseData = response.body().string();
                    L.d("评论的返回结果 = "+responseData);
                    JSONObject data = new JSONObject(responseData);
                    L.d("data "+ data);
                    int code = data.getInt("code");
                    if (code == 0){

                    }else if (code ==1){
                        Looper.prepare();
                        Toast.makeText(getActivity().getApplicationContext(),"登录信息已失效，请重新登陆！ ",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        Toast.makeText(getActivity().getApplicationContext(),"评论失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void setHeight(){
//        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mListView.getLayoutParams();
        ViewGroup.LayoutParams params1 = mListView.getLayoutParams();

        L.d("高度  cp "+(150*h));
        params1.height= Utils.dip2px(Objects.requireNonNull(getActivity()),150)*h;

        mListView.setLayoutParams(params1);
    }

    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 150;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0,len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1));
        L.d("设置的高度 = "+params.height);
        L.d("设置的高度 hou = "+totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1)));
        listView.setLayoutParams(params);
    }


    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    dialog.dismiss();
                    mAdapter.addTheCommentData(LoginData.getName(), DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"),commentContent);
                    SendCommentWithOkHttp(commentContent,mCourseId,lessonId,0,0);
                    //去掉暂无评论显示
                    tv_no_comment.setVisibility(View.GONE);
                    //刷新冰设置高度
                    setListViewHeight(mListView);
                    mListView.postInvalidate();
//                    new DisplayCommentAsyncTask().execute();

                    Toast.makeText(getActivity(),"评论成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + contentList.get(0).records.get(position).name + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    L.d("针对这个id的评论回复 listDatas.get(0).content.records.get(position).id " +contentList.get(0).records.get(position).id);
                    mAdapter.addTheReplyData(LoginData.getName(),replyContent,position);
                    //SendCommentWithOkHttp(String content ,int courseId,int lessonId,int pid,int type )
                    SendCommentWithOkHttp(replyContent,mCourseId,lessonId
                            ,contentList.get(0).records.get(position).id,0);
                    setListViewHeight(mListView);
                    mListView.postInvalidate();
                    Toast.makeText(getActivity(),"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
