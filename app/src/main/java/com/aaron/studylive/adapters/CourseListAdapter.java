package com.aaron.studylive.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.CourseListData;
import com.aaron.studylive.utils.L;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/24.
 */
public class CourseListAdapter extends BaseAdapter {

    private List<CourseListData> listDatas;

    private LayoutInflater inflater;

    private Context mContext;

    public CourseListAdapter(Context context, List<CourseListData> list) {
        listDatas = list;
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

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CourseListData data = listDatas.get(i);
        ViewHodler hodler = null;

        if (view == null) {
            hodler = new ViewHodler();
            view = inflater.inflate(R.layout.fragment_course_item, null);

            hodler.img = ButterKnife.findById(view, R.id.img);
            hodler.title = ButterKnife.findById(view, R.id.tv_title);
            hodler.numbers = ButterKnife.findById(view, R.id.tv_number);
            hodler.des = ButterKnife.findById(view,R.id.tv_class_des);
            L.d("CourseListAdapter ::hodler.img"+hodler.img);
            L.d("data.getPic:::"+listDatas.get(i).getPic());

            view.setTag(hodler);
        } else {
            hodler = (ViewHodler) view.getTag();
        }

        Picasso.with(mContext).load(data.getPic()).placeholder(R.drawable.bg_default).into(hodler.img);
        hodler.title.setText(String.format("%s", data.getName()));
        hodler.des.setText(String.format("%s", data.getDesc()));
        hodler.numbers.setText(String.format("%d", data.getNumbers()));


//        if (data.getFinished() == 0) {
//            hodler.finished.setTextColor(mContext.getResources().getColor(R.color.course_update_text));
//            String update = "更新至"+data.getMaxChapterSeq()+"-"+data.getMaxMediaSeq();
//            hodler.finished.setText(update);
//        } else {
//            hodler.finished.setTextColor(mContext.getResources().getColor(R.color.course_second_text));
//            hodler.finished.setText("更新完成");
//        }

        return view;
    }



    private static class ViewHodler {
        ImageView img;
        TextView title;
        TextView numbers;
        TextView des;
    }


}
