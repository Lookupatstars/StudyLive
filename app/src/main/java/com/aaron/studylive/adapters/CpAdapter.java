package com.aaron.studylive.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.CpData;
import com.aaron.studylive.utils.L;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/6/1.
 */
public class CpAdapter extends BaseAdapter {

    private List<CpData> listDatas;

    private LayoutInflater inflater;

    private Context mContext;

    public CpAdapter(Context context, List<CpData> list) {
        this.listDatas = list;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }


    @Override
    public int getCount() {

        return listDatas.size();
    }

    @Override
    public CpData getItem(int i) {
        return listDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CpData data = listDatas.get(i);
        ViewHodler hodler = null;

        if (view == null) {
            hodler = new ViewHodler();
            view = inflater.inflate(R.layout.fragment_cp_content, null);

            hodler.rl_cp_list = ButterKnife.findById(view,R.id.rl_cp_list);
            hodler.playImage = ButterKnife.findById(view, R.id.iv_cp_play);
            hodler.title = ButterKnife.findById(view, R.id.tv_cp_title);
            hodler.time = ButterKnife.findById(view, R.id.tv_cp_time);

            L.d("CpAdapter =  "+data.getName());
            L.d("CpAdapter 播放地址getResourceAddress2 =  "+data.getResourceAddress2());
            view.setTag(hodler);
        } else {
            hodler = (ViewHodler) view.getTag();
        }

        if (data.getName().contains("-")){
            hodler.title.setText(String.format("%s", data.getName()));
        }else {
            hodler.title.setText(String.format("%d %s", i+1, data.getName()));
        }

        hodler.time.setText(String.format("%s 分钟", data.getCourseTime()));
        if (data.isSeleted()){

            hodler.playImage.setImageResource(R.drawable.cp_play_press);
            hodler.title.setTextColor(mContext.getResources().getColor(R.color.red));
            hodler.time.setTextColor(mContext.getResources().getColor(R.color.red));
        }else {
            if (data.isSeletedEnd()){
                hodler.playImage.setImageResource(R.drawable.cp_play_normal);
                hodler.title.setTextColor(mContext.getResources().getColor(R.color.grey));
                hodler.time.setTextColor(mContext.getResources().getColor(R.color.grey));
            }else {
                hodler.playImage.setImageResource(R.drawable.cp_play_normal);
                hodler.title.setTextColor(mContext.getResources().getColor(R.color.black));
                hodler.time.setTextColor(mContext.getResources().getColor(R.color.black));
            }

        }


        return view;
    }

    private static class ViewHodler {
        RelativeLayout rl_cp_list;
        ImageView playImage;
        TextView title;
        TextView time;
    }

    /**
     * 秒转化为常见格式
     * @param time
     * @return
     */
    private String sec2time(long time) {
        //初始化Formatter的转换格式。
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String hms = formatter.format(time);

        return hms;
    }

}
