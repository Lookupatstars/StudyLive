package com.aaron.studylive.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.ClassifyData;

import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/25.
 */
public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {

    private List<ClassifyData> listDatas;

    private Context mContext;

    public ClassifyAdapter(Context context, List<ClassifyData> list) {
        listDatas = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_classify_item_title, parent, false);
            view.setTag(0);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_classify_item_content, parent, false);
            view.setTag(1);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassifyData data = listDatas.get(position);
        if (isSection(position)) {
            holder.title.setText(data.getName() + "");
        } else {
            holder.name.setText(data.getName()+"");
        }

        Random random = new Random();
        int r = 30 + random.nextInt(200);
        int g = 30 + random.nextInt(200);
        int b = 30 + random.nextInt(200);
        holder.title.setTextColor(Color.rgb(r, g, b));
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (isSection(position))
            return 0;

        return 1;
    }

    public boolean isSection(int position) {
            return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView name;
        TextView number;

        public ViewHolder(final View itemView) {
            super(itemView);
            if ((int)itemView.getTag() == 0) {
                title = ButterKnife.findById(itemView, R.id.tv_title);
            } else {
                name = ButterKnife.findById(itemView, R.id.tv_name);
                number = ButterKnife.findById(itemView, R.id.tv_number);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
