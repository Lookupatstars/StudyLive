package com.aaron.studylive.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aaron.studylive.MainActivityWithTab;
import com.aaron.studylive.R;
import com.aaron.studylive.bean.ClassMenu.ClassMenuData;
import com.aaron.studylive.widget.implement.ClassPager;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/5/8
 * The current project is StudyLive
 *
 * @Describe:  侧边栏的fragment
 */
@SuppressWarnings("ALL")
public class LeftMenuFragment extends BaseFragment {

    private ListView lv_list;//接受的courseWeb的content内容
    private ArrayList<ClassMenuData> course_web_content;
    private LeftMenuAdapter adapter;

//    private int currentPos;//当前的listView的位置

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu,null);
        lv_list = view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {

    }

    //个侧边栏设置数据
    public void setMenuData(ArrayList<ClassMenuData> content){
//        mCurrentPos = 0;//让listView清零
        //更新页面
        course_web_content = content;
        adapter = new LeftMenuAdapter();
        //显示adapter
        lv_list.setAdapter(adapter);

        //设置点击事件,
        // 对本项目来说，可以直接默认点击
        lv_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                currentPos = position; //更新位置
                //当item被点击之后的处理，也就是。直接替换fragment
                adapter.notifyDataSetChanged();//刷新listView

                //收起侧边栏
//                toggle();

                //侧边蓝点击之后，要修改直播课的FrameLayout的内容
                setCurrentDetailPager(position);
            }
        });
    }

    //设置当前的菜单详情页
    private void setCurrentDetailPager(int position) {
        //获取直播课程的对象 ClassPager
        //先获取MainActivity
        MainActivityWithTab mainUI = (MainActivityWithTab) mActivity;
        //获取ContentFragment
        ContentFragment fragment = mainUI.getContentFragment();
        //获取ClassPager
        ClassPager classPager = fragment.getClassPager();
        //修改课程的FrameLayout的布局
//        classPager.setCurrentDetailPager(position);

    }

    //打开或关闭侧边蓝
//    private void toggle() {
//        MainActivityWithTab mainUI = (MainActivityWithTab) mActivity;
//
//    }

    //
    class LeftMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return course_web_content.size();
        }

        @Override
        public ClassMenuData getItem(int position) {
            return course_web_content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity,R.layout.list_item_left_menu,null);
            TextView tv_left_menu = view.findViewById(R.id.tv_left_menu);
            ClassMenuData classMenuData = getItem(position);
            tv_left_menu.setText(classMenuData.name);

//            if (position == currentPos){
//                //被选中
//                tv_left_menu.setEnabled(true); //变为红色
//            }else {
//                //未被选中
//                tv_left_menu.setEnabled(false); //变成白色
//            }

            return view;
        }
    }


}
