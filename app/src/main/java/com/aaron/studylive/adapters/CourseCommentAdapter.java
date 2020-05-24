package com.aaron.studylive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.CommentContent;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.utils.Utils;

import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/6/1.
 */
public class CourseCommentAdapter extends BaseAdapter {

    private List<CommentContent> listDatas;
    private LayoutInflater inflater;
    private Context mContext;
    private int count;
    private ViewHolder holder = null;

    private CourseCommentReplayAdapter adapter;

    public CourseCommentAdapter(Context context, List<CommentContent> list) {
        this.listDatas = list;
        inflater = LayoutInflater.from(context);
        mContext = context;
        L.d("复制后的listdata = "+listDatas);
    }

    @Override
    public int getCount() {
        return listDatas.get(0).records.size();
    }

    @Override
    public Object getItem(int i) {
        return listDatas.get(0).records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        this.count = i;
        CommentContent.CommentContentRecords data = listDatas.get(0).records.get(i);


        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_course_comment_item, null);

//            holder.image = ButterKnife.findById(view, R.id.img);
            holder.name = ButterKnife.findById(view, R.id.tv_comment_name);
            holder.time = ButterKnife.findById(view, R.id.tv_comment_time);
            holder.content = ButterKnife.findById(view, R.id.tv_comment_content);
            holder.lv_replay = ButterKnife.findById(view,R.id.lv_replay);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

//        Picasso.with(mContext).load(data.getImg()).into(holder.image);

        holder.name.setText(data.name);
        holder.time.setText(data.createTime);
        holder.content.setText(data.content);

        if (data.replys != null && data.replys.size() !=0){
            if (data.id == data.replys.get(0).pid){
                adapter = new CourseCommentReplayAdapter(mContext,data.replys);
                holder.lv_replay.setAdapter(adapter);
            }else {
                holder.lv_replay.setVisibility(View.GONE);
            }

        }else {
            holder.lv_replay.setVisibility(View.GONE);
        }

//        holder.lv_replay.setLayoutParams();
        return view;
    }

    //回复评论
    public void addTheReplyData(String name, String content, int position){

        ViewHolder viewHolder = new ViewHolder();
        if(!content.equals("")){
            CommentContent.CommentContentRecords.CommentContentRecordsReplays data = new CommentContent.CommentContentRecords.CommentContentRecordsReplays();
            data.name =name;
            data.content = content;

            listDatas.get(0).records.get(position).replys.add(data);

            ViewGroup.LayoutParams params1 = viewHolder.lv_replay.getLayoutParams();

//            L.d("高度  cp "+(150*h));
            params1.height= Utils.dip2px(Objects.requireNonNull(mContext),150)*1;

            viewHolder.lv_replay.setLayoutParams(params1);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }
        notifyDataSetChanged();
    }


//    //为listview动态设置高度（有多少条目就显示多少条目）
//    public void setListViewHeight(ListView listView) {
//        //获取listView的adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 150;
//        //listAdapter.getCount()返回数据项的数目
//        for (int i = 0,len = listAdapter.getCount(); i < len; i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1));
//        L.d("设置的高度 = "+params.height);
//        L.d("设置的高度 hou = "+totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1)));
//        listView.setLayoutParams(params);
//    }


    private static class ViewHolder {
        TextView name;
        TextView time;
        TextView content;
        ImageView image;
        ListView lv_replay;
    }

    //评论
    public void addTheCommentData(String name, String time, String content){
        if(!content.equals("")){
            CommentContent.CommentContentRecords data = new CommentContent.CommentContentRecords();
            data.name = name;
            data.createTime = time;
            data.content = content;
            data.replys = null;
            listDatas.get(0).records.add(data);

//            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }
        notifyDataSetChanged();
    }




}
