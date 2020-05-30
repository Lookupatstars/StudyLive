package com.aaron.studylive.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.ClassInCommentData;
import com.aaron.studylive.bean.CommentContentRecordsReplays;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<ClassInCommentData> commentBeanList;
    private List<CommentContentRecordsReplays> replyBeanList;
    private Context context;
    private int pageIndex = 1;
    private Bitmap img;

    public CommentExpandAdapter(Context context, List<ClassInCommentData> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.get(0).records.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(0).records.get(i).replys == null){
            return 0;
        }else {
            return commentBeanList.get(0).records.get(i).replys.size()>0 ? commentBeanList.get(0).records.get(i).replys.size():0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(0).records.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(0).records.get(i).replys.get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;


        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_course_comment_item, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.logo.setImageBitmap(commentBeanList.get(0).records.get(groupPosition).bm);
        groupHolder.tv_name.setText(commentBeanList.get(0).records.get(groupPosition).name);
        groupHolder.tv_time.setText(commentBeanList.get(0).records.get(groupPosition).createTime.replace("T"," "));
        groupHolder.tv_content.setText(commentBeanList.get(0).records.get(groupPosition).content);
//        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isLike){
//                    isLike = false;
//                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
//                }else {
//                    isLike = true;
//                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
//                }
//            }
//        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = commentBeanList.get(0).records.get(groupPosition).replys.get(childPosition).name;
        if(!TextUtils.isEmpty(replyUser)){
            childHolder.tv_name.setText(replyUser + ":");
        }else {
            childHolder.tv_name.setText("无名"+":");
        }

        childHolder.tv_content.setText(commentBeanList.get(0).records.get(groupPosition).replys.get(childPosition).content);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private ImageView logo;
        private TextView tv_name, tv_content, tv_time;
//        private ImageView iv_like;
        public GroupHolder(View view) {
            logo = view.findViewById(R.id.img);
            tv_content = (TextView) view.findViewById(R.id.tv_comment_content);
            tv_name = (TextView) view.findViewById(R.id.tv_comment_name);
            tv_time = (TextView) view.findViewById(R.id.tv_comment_time);
//            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder{
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     * 新的评论数据
     */
    public void addTheCommentData(ClassInCommentData.CommentContentRecords data){
        if(data != null){

            commentBeanList.get(0).records.add(data);

            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     * 新的回复数据
     */
    public void addTheReplyData(CommentContentRecordsReplays recordsReplays, int groupPosition){
        if(recordsReplays!=null){
//            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+recordsReplays.toString() );
            if(commentBeanList.get(0).records.get(groupPosition).getReplys() != null ){
                commentBeanList.get(0).records.get(groupPosition).getReplys().add(recordsReplays);

            }else {
                List<CommentContentRecordsReplays> replyList = new ArrayList<>();
                replyList.add(recordsReplays);
                commentBeanList.get(0).records.get(groupPosition).setReplys(replyList);
            }

            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

}
