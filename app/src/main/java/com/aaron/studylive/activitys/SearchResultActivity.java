package com.aaron.studylive.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.ActivityCollector;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Aaron on 2020/5/17
 * The current project is StudyLive
 *
 * @Describe:
 */
public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActvity";
    private TextView tv_search_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

    }
//
//    // 解析搜索请求页面传来的搜索信息，并据此执行搜索查询操作
//    private void doSearchQuery(Intent intent) {
//        if (intent != null) {
//            // 如果是通过ACTION_SEARCH来调用，即为搜索框来源
//            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//                // 获取额外的搜索数据
//                Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
//                String value = bundle.getString("hi");
//                // 获取实际的搜索文本
//                String queryString = intent.getStringExtra(SearchManager.QUERY);
//                tv_search_result.setText("您输入的搜索文字是："+queryString+", 额外信息："+value);
//            }
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // 从menu_null.xml中构建菜单界面布局
//        getMenuInflater().inflate(R.menu.menu_null, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) { // 点击了工具栏左边的返回箭头
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
