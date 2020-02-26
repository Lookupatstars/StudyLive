package com.aaron.studylive.utils;

import android.os.Build;
import android.util.Log;

import com.aaron.studylive.BuildConfig;

public class L {
    private static final String TAG = "gsj";
    private static boolean sDebug = BuildConfig.DEBUG;

    public static void d(String msg, Object... args){
        if(!sDebug){
            return;
        }
        Log.d(TAG,String.format(msg, args));
    }
}
