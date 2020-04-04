package com.aaron.studylive.utils;

import android.content.Context;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe:   网络缓存的工具类
 */
public class CacheUtils {

//    直接调用PreUtils工具类

    //设置缓存、写缓存
    //key-value   =  url-json 保存在本地,通过 sharepreference
    public static void setCache(String url, String json, Context context){
        PrefUtils.setString(context,url ,json);
    }

    //获取缓存
    public static String getCache(String url, Context context){
        return PrefUtils.getString(context,url,null);//default 没有缓存时是null
    }
}
