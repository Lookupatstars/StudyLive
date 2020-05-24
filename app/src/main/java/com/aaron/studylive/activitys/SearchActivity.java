package com.aaron.studylive.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.SearchData;
import com.aaron.studylive.database.SearchDBHelper;
import com.aaron.studylive.database.SearchRecordsDBHelper;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.views.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

/**
 * Created by Aaron on 2020/5/19
 * The current project is StudyLive
 *
 * @Describe:  搜索界面
 */
public class SearchActivity extends AppCompatActivity {

//    EditText mEditSearch;
//    TextView mTvSearch;
    SearchView sv_search_display;
    TextView mTvTip;
    ListViewForScrollView mListView;
    TextView mTvClear;

    SimpleCursorAdapter adapter;

    SearchDBHelper searchDBHelper;
    SearchRecordsDBHelper searchRecordsDBHelper;
    SQLiteDatabase db_search;
    SQLiteDatabase db_records;
    Cursor cursor;

    private static final String ImgUrl = "http://course-api.zzu.gdatacloud.com:890/";
    private List<SearchData> searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActivityCollector.addActivity(this);

        searchData = new ArrayList<>();
        initView();

        initListener();
        new SearchAsyncTask().execute();
    }

    private void analysisSearchNemaData(String s) {
        L.d("analysisSearchNemaData = "+s);
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");

            if (errorCode == 0) {
                JSONArray array = object.getJSONObject("content").getJSONArray("records");
                L.d("araay =  "+array);
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    SearchData data = new SearchData();

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setDesc(object.getString("summary"));
                    data.setNumbers(object.getInt("viewCount"));
                    data.setPic(ImgUrl + object.getString("img"));
                    data.setThumb(ImgUrl + object.getString("img2"));
                    data.setNumLession(object.getInt("lessonNum"));
                    data.setClassType(object.getInt("type"));

                    searchData.add(data);
                }
                initDatas();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class SearchAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getRelevantCourse();
            L.d("CommentAsyncTask  url =  "+url);
            return HttpRequest.getInstance().GET(SearchActivity.this,url,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisSearchNemaData(s);
        }
    }


    private void initView() {
        sv_search_display = findViewById(R.id.sv_search_display);
        sv_search_display.setSubmitButtonEnabled(true);
        TextView searchText = sv_search_display.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.blue));

        mTvTip = findViewById(R.id.tv_tip);
        mListView = findViewById(R.id.listView);
        mTvClear = findViewById(R.id.tv_clear_history);
    }

    private void initDatas() {
        searchDBHelper = new SearchDBHelper(SearchActivity.this);
        searchRecordsDBHelper = new SearchRecordsDBHelper(SearchActivity.this);
        // TODO: 2017/8/10 1、初始化本地数据库
        initializeData();

        // TODO: 2017/8/10 2、尝试从保存查询纪录的数据库中获取历史纪录并显示
        cursor = searchRecordsDBHelper.getReadableDatabase().rawQuery("select * from searchRecords_info", null);
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor
                , new String[]{"name"}, new int[]{android.R.id.text1}
                , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mListView.setAdapter(adapter);
    }
    /**
     * 避免重复初始化数据
     */
    private void deleteData() {
        db_search = searchDBHelper.getWritableDatabase();
        db_search.execSQL("delete from search_info");
        db_search.close();
    }

    /**
     * 初始化数据
     */
    private void initializeData() {
        deleteData();
        db_search = searchDBHelper.getWritableDatabase();

        L.d("看看数据有诶呦 = "+searchData.size());

        for (int i = 0; i < searchData.size(); i++) {
            db_search.execSQL("insert into search_info values(null,?)",
                    new String[]{searchData.get(i).getName()});
        }
        db_search.close();
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {
        /**
         * 清除历史纪录
         */
        mTvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecords();
            }
        });



        /**
         * EditText对键盘搜索按钮的监听，保存搜索纪录，隐藏软件盘
         */
//        // 搜索及保存搜索纪录

//        sv_search_display.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    //隐藏键盘
//                    ((InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    //保存搜索记录
//                    insertRecords(mEditSearch.getText().toString().trim());
////                }
//                return false;
//            }
//        });

//        mEditSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//
//                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    //隐藏键盘
//                    ((InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    //保存搜索记录
//                    insertRecords(mEditSearch.getText().toString().trim());
//                }
//
//                return false;
//            }
//        });
        /**
         * EditText搜索框对输入值变化的监听，实时搜索
         */
        // 实现 实时搜索，点击搜索按钮，保存搜索纪录，
        sv_search_display.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //直接点击搜索按钮，保存搜索纪录，隐藏软键盘
                ((InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //保存搜索记录
                insertRecords(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    mTvTip.setText("搜索历史");
                    mTvClear.setVisibility(View.VISIBLE);
                    cursor = searchRecordsDBHelper.getReadableDatabase().rawQuery("select * from searchRecords_info", null);
                    refreshListView();
                }else {
                    mTvTip.setText("搜索结果");
                    mTvClear.setVisibility(View.GONE);
                    queryData(newText);
                }

                return true;
            }
        });

//        mEditSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (mEditSearch.getText().toString().equals("")) {
//                    mTvTip.setText("搜索历史");
//                    mTvClear.setVisibility(View.VISIBLE);
//                    cursor = searchRecordsDBHelper.getReadableDatabase().rawQuery("select * from searchRecords_info", null);
//                    refreshListView();
//                } else {
//                    mTvTip.setText("搜索结果");
//                    mTvClear.setVisibility(View.GONE);
//                    String searchString = mEditSearch.getText().toString();
//                    queryData(searchString);
//                }
//            }
//        });

        /**
         * ListView的item点击事件
         */
        // listview的点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                L.d("name 是多少 = "+name);
                insertRecords(name);
                int i = queryDataByName(name);
                L.d("i是都少 = "+i);
                if (i == -1){
                    Toast.makeText(SearchActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
                    return;
                }
                SearchData data = searchData.get(i);
                Intent intent = new Intent(SearchActivity.this, DetailPlayerActivity.class);
                if (data.getNumLession() == 0){
                    Toast.makeText(SearchActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("id", data.getId());
                intent.putExtra("title", data.getName());
                intent.putExtra("lessionNum", data.getNumLession());
                intent.putExtra("thumb",data.getThumb());
                intent.putExtra("summary",data.getDesc());
                intent.putExtra("type",data.getClassType());
                startActivity(intent);
                SearchActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                finish();
            }
        });

    }

    /**
     * 保存搜索纪录
     *
     */
    private void insertRecords(String name) {
        if (!hasDataRecords(name)) {
            db_records = searchRecordsDBHelper.getWritableDatabase();
            db_records.execSQL("insert into searchRecords_info values(null,?)", new String[]{name});
            db_records.close();
        }
    }

    /**
     * 检查是否已经存在此搜索纪录
     *
     * @param records
     * @return
     */
    private boolean hasDataRecords(String records) {

        cursor = searchRecordsDBHelper.getReadableDatabase()
                .rawQuery("select _id,name from searchRecords_info where name = ?"
                        , new String[]{records});
        return cursor.moveToNext();
    }

    /**
     * 搜索数据库中的数据
     * @param searchData
     */
    private void queryData(String searchData) {
        cursor = searchDBHelper.getReadableDatabase()
                .rawQuery("select * from search_info where name like '%" + searchData + "%'", null);
        refreshListView();
    }

    /**
     * 删除历史纪录
     */
    private void deleteRecords() {
        db_records = searchRecordsDBHelper.getWritableDatabase();
        db_records.execSQL("delete from searchRecords_info");

        cursor = searchRecordsDBHelper.getReadableDatabase().rawQuery("select * from searchRecords_info", null);
//        if (sv_search_display.getTe().toString().equals("")) {
            refreshListView();
//        }
    }

    /**
     * 刷新listview
     */
    private void refreshListView() {
        adapter.notifyDataSetChanged();
        adapter.swapCursor(cursor);
    }

    private int queryDataByName(String name){
      //存储选择的课程的在数据中的位置
        L.d("  queryDataByName  = "+searchData.size());
        for (int i = 0; i < searchData.size(); i++) {
            if (searchData.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db_records != null) {
            db_records.close();
        }
        if (db_search != null) {
            db_search.close();
        }
    }

}
