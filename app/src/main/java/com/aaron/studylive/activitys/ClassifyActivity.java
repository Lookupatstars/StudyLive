package com.aaron.studylive.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.adapters.ClassifyAdapter;
import com.aaron.studylive.base.BaseActivity;
import com.aaron.studylive.bean.ClassifyData;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Bind;

/**
 * Created by recker on 2020/5/23.
 *
 * 课程分类
 *
 */
public class ClassifyActivity extends BaseActivity implements
        View.OnClickListener, ClassifyAdapter.OnItemClickListener{

    @Bind(R.id.iv_classify_back)
    ImageView mIvBack;

    @Bind(R.id.iv_search_classify)
    ImageView mIvSearch;

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    private List<ClassifyData> listDatas;

    private ClassifyAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify;
    }

    @Override
    protected void init() {
        ActivityCollector.addActivity(this);
        initView();
        new ClassifyAsyncTask().execute();
        setupClick();
    }


    private void initView() {
        listDatas = new ArrayList<>();
        mAdapter = new ClassifyAdapter(this, listDatas);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mAdapter.isSection(position) ? layoutManager.getSpanCount() : 1 ;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void setupClick() {
        mIvBack.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_classify_back:
                finish();
                break;
            case R.id.iv_search_classify:
                Intent intent = new Intent(ClassifyActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");

            if (errorCode == 0) {

                JSONArray array = object.getJSONArray("content");
                for (int i = 0; i < array.length(); i++) {
                    ClassifyData data1 = new ClassifyData();
                    object = array.getJSONObject(i);

                    data1.setId(object.getInt("id"));
                    data1.setName(object.getString("name"));
                    data1.setPid(object.getInt("pid"));

                    listDatas.add(data1);
                }

                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(ClassifyActivity.this,"暂未实现",Toast.LENGTH_SHORT).show();

//        ClassifyData data = listDatas.get(position);
//        if (!data.isTitle()) {
//            Intent intent = new Intent(this, ClassifyListActivity.class);
//            intent.putExtra("title", data.getName()+"");
//            intent.putExtra("url", data.getPic());
//            intent.putExtra("id", data.getId());
//            startActivity(intent);
//        }
    }


    private class ClassifyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getClassifListUrl();
            return HttpRequest.getInstance().GET(ClassifyActivity.this,url, null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mProgressBar.setVisibility(View.GONE);
            analysisJsonData(s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
