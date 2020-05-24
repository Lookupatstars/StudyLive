package com.aaron.studylive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.CommentContent;
import com.aaron.studylive.utils.L;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/6/1.
 */
public class CourseCommentReplayAdapter extends BaseAdapter {

    private ArrayList<CommentContent.CommentContentRecords.CommentContentRecordsReplays> listDatas;
    private LayoutInflater inflater;
    private Context mContext;

    public CourseCommentReplayAdapter(Context context, ArrayList<CommentContent.CommentContentRecords.CommentContentRecordsReplays> list) {
        this.listDatas = list;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return listDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CommentContent.CommentContentRecords.CommentContentRecordsReplays data = listDatas.get(0);
        L.d("data.name = "+data.name);
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_course_comment_replay_item, null);

            holder.name = ButterKnife.findById(view, R.id.tv_comment_reply_name);
            holder.content = ButterKnife.findById(view, R.id.tv_comment_reply_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

//        Picasso.with(mContext).load(data.getImg()).into(holder.image);

        holder.name.setText(data.name+"：");
        holder.content.setText(data.content+"");

        return view;
    }


    private static class ViewHolder {
        TextView name;
        TextView content;
    }

}
