package com.aaron.studylive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.SearchData;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Aaron on 2020/5/16
 * The current project is StudyLive
 *
 * @Describe:  搜索的显示
 */
public class SearchAdapter extends BaseAdapter {

    private List<SearchData> listSearchData;

    private LayoutInflater inflater;

    private Context mContext;

    public SearchAdapter(Context context, List<SearchData> data) {
        listSearchData = data;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return listSearchData.size();
    }

    @Override
    public SearchData getItem(int position) {
        return listSearchData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SearchData data = listSearchData.get(position);
        ViewHolderSearch holderSearch = null;

        if (convertView ==null){
            holderSearch = new ViewHolderSearch();
            convertView = inflater.inflate(R.layout.fragment_search_item, null);    //绑定Adapter的显示XML
            //viewHolder绑定对应的控件ID
            holderSearch.title = ButterKnife.findById(convertView,R.id.tv_search_list_title);
            //把viewHodler添加给View
            convertView.setTag(holderSearch);
        } else {
            holderSearch = (ViewHolderSearch) convertView.getTag();
        }

        //给ViewHolder设置数据
        holderSearch.title.setText(data.getName());

        return convertView;
    }

    private static class ViewHolderSearch {
        TextView title;
    }
}
