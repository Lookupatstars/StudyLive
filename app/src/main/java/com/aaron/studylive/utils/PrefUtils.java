package com.aaron.studylive.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aaron on 2020/3/28
 * The current project is StudyLive
 *
 * @Describe: SharePreference封装
 */
public class PrefUtils {

    //boolen
    public static boolean getBoolen(Context ctx,String key, boolean defValue){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    public static void setBoolen(Context ctx,String key, boolean value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();//apply()代替了commit()
    }

    //String
    public static String getString(Context ctx,String key, String defValue){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void setString(Context ctx,String key, String value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();//apply()代替了commit()
    }

    //int
    public static int getInt(Context ctx,String key, int defValue){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static void setInt(Context ctx,String key, int value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();//apply()代替了commit()
    }
}
