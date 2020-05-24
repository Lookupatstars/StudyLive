package com.aaron.studylive.utils;

import android.content.Intent;

import com.aaron.studylive.bean.CourseListData;

/**
 * Created by Aaron on 2020/5/11
 * The current project is StudyLive
 *
 * @Describe:  把课程的部分数据 从 HomeFragment和ClassFragment 传递到课程详情页DetailPlayerActivity
 */
public class Class2detail {

    public Intent TransformData(Intent intent, CourseListData data){


        intent.putExtra("id", data.getId());
        intent.putExtra("title", data.getName());
        intent.putExtra("lessionNum", data.getNumLession());
        intent.putExtra("thumb",data.getThumb());
        intent.putExtra("summary",data.getDesc());
        intent.putExtra("type",data.getClassType());

        L.d("onItemClick::data.getId()  = "+data.getId());
        L.d("onItemClick::data.getName()  = "+data.getName());
        L.d("onItemClick::data.getThumb()  = "+data.getThumb());
        L.d("onItemClick::data.getNumLession()  = "+data.getNumLession());
        L.d("onItemClick::data.getDesc()  = "+data.getDesc());
        L.d("onItemClick::data.getClassType()  = "+data.getClassType());
        return intent;
    }
}
