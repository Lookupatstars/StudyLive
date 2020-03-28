package com.aaron.studylive.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.LiveClassInfo;

import java.util.ArrayList;
import com.aaron.studylive.widget.RecyclerExtras.OnItemClickListener;
import com.aaron.studylive.widget.RecyclerExtras.OnItemLongClickListener;

/**
 * Created by Aaron on 2020/3/24
 * The current project is StudyLive
 *
 * @Describe:  直播课的适配器
 */

@SuppressLint("DefaultLocale")
public class LiveClassAdapter extends RecyclerView.Adapter<ViewHolder>
        implements OnItemClickListener, OnItemLongClickListener {
    private final static String TAG = "RecyclerStaggeredAdapter";
    private Context mContext; // 声明一个上下文对象
    private ArrayList<LiveClassInfo> liveClassInfos;

    public LiveClassAdapter(Context context, ArrayList<LiveClassInfo> liveClassInfosArray) {
        mContext = context;
        liveClassInfos = liveClassInfosArray;
    }

    //获取列表项个数
    @Override
    public int getItemCount() {
        return liveClassInfos.size();
    }

    // 创建列表项的视图持有者
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // 根据布局文件item_staggered.xml生成视图对象
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_hor_live_class, viewGroup, false);
        return new ItemHolder(v);
    }

    // 绑定列表项的视图持有者
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tv_class_title.setText(liveClassInfos.get(position).live_class_title);
        holder.tv_class_teacher.setText(liveClassInfos.get(position).live_class_teacher);
        holder.tv_class_name.setText(liveClassInfos.get(position).live_class_name);
        holder.tv_class_time.setText(liveClassInfos.get(position).live_class_time);
        holder.iv_class_image.setBackgroundResource(liveClassInfos.get(position).live_class_item_id);

        holder.ll_live_class_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.ll_live_class_item){
                    if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                    }
                }
            }
        });

//        LayoutParams params = holder.ll_item.getLayoutParams();
//        params.height = (int) Math.round(300 * Math.random());
//        if (params.height < 60) {
//            params.height = 60;
//        }
//        // 很奇怪，setLayoutParams对瀑布流网格不起作用，只能用setHeight
//        holder.tv_title.setHeight(params.height);

//        // 列表项的长按事件需要自己实现
//        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemLongClickListener != null) {
//                    mOnItemLongClickListener.onItemLongClick(v, position);
//                }
//                return true;
//            }
//        });

    }

    // 获取列表项的类型
    public int getItemViewType(int position) {
        return 0;
    }

    // 获取列表项的编号
    public long getItemId(int position) {
        return position;
    }

    // 定义列表项的视图持有者
    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_live_class_item; // 直播公开课的线性布局
        public ImageView iv_class_image; // 直播公开课的教师头像视图
        public TextView tv_class_title; // 直播公开课的标题视图
        public TextView tv_class_name;
        public TextView tv_class_teacher;
        public TextView tv_class_time;

        public ItemHolder( View itemView) {
            super(itemView);
            ll_live_class_item = itemView.findViewById(R.id.ll_live_class_item);
            iv_class_image = itemView.findViewById(R.id.iv_class_image);
            tv_class_title = itemView.findViewById(R.id.tv_class_title);
            tv_class_name = itemView.findViewById(R.id.tv_class_name);
            tv_class_teacher = itemView.findViewById(R.id.tv_class_teacher);
            tv_class_time = itemView.findViewById(R.id.tv_class_time);
        }
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
                liveClassInfos.get(position).live_class_title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    // 处理列表项的长按事件
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项，商品名称是%s", position + 1,
                liveClassInfos.get(position).live_class_name);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }
}
