package com.aaron.studylive.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.LoveClassInfo;
import com.aaron.studylive.widget.RecyclerExtras.OnItemClickListener;
import com.aaron.studylive.widget.RecyclerExtras.OnItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/24
 * The current project is StudyLive
 *
 * @Describe:  直播课的适配器
 */

@SuppressLint("DefaultLocale")
public class LoveClassAdapter extends BaseAdapter
        implements OnItemClickListener, OnItemLongClickListener{
    private final static String TAG = "RecyclerStaggeredAdapter";
    private Context mContext; // 声明一个上下文对象
    private ArrayList<LoveClassInfo> loveClassInfos; //信息队列

    public LoveClassAdapter(Context context, ArrayList<LoveClassInfo> loveClassInfosArray) {
        mContext = context;
        loveClassInfos = loveClassInfosArray;
    }

    //列表项个数
    @Override
    public int getCount() {
        return loveClassInfos.size();
    }

    //获取列表项数据
    @Override
    public Object getItem(int position) {
        return loveClassInfos.get(position);
    }

    // 获取列表项的编号
    public long getItemId(int position) {
        return position;
    }

    //获取指定位置的列表项视图
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){ //转换视图为空
            holder = new ViewHolder();
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ver_love_class, null);
            holder.iv_love_image = convertView.findViewById(R.id.iv_love_image);
            holder.tv_love_title = convertView.findViewById(R.id.tv_love_title);
            holder.tv_love_teacher = convertView.findViewById(R.id.tv_love_teacher);
            holder.tv_love_hot = convertView.findViewById(R.id.tv_love_hot);
            holder.ll_love_class_item = convertView.findViewById(R.id.ll_love_class_item);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else { // 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
        LoveClassInfo loveClassInfo = loveClassInfos.get(position);
        holder.iv_love_image.setImageResource(loveClassInfo.love_class_image_id); // 显示图片
        holder.tv_love_title.setText(loveClassInfo.love_class_title); // 显示名称
        holder.tv_love_teacher.setText(loveClassInfo.love_class_teacher);
        holder.tv_love_hot.setText(String.format("%d", loveClassInfo.love_class_hot));

        holder.ll_love_class_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ll_love_class_item){
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(v,position);
                    }
                }
            }
        });
        return convertView;
    }

    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder{
        public LinearLayout ll_love_class_item; // 直播公开课猜你喜欢的线性布局
        public ImageView iv_love_image; // 直播公开课的教师头像视图
        public TextView tv_love_title; // 直播公开课的标题视图
        public TextView tv_love_teacher;
        public TextView tv_love_hot;
    }

    // 声明列表项的点击监听器对象
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 声明列表项的长按监听器对象
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    // 处理列表项的点击事件
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项，课程名称是%s", position + 1,
                loveClassInfos.get(position).love_class_title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }


    // 处理列表项的长按事件
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项，商品名称是%s", position + 1,
                loveClassInfos.get(position).love_class_title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }
}
