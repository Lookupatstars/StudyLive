package com.aaron.studylive.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2020/4/22
 * The current project is StudyLive
 *
 * @Describe: 提供一个专门的集合类对所有的活动进行管理
 * 方便随时退出程序
 */
public class ActivityCollector {
    public static List<Activity> sActivities=new ArrayList<>();


    public static void addActivity(Activity activity){
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity){
        sActivities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:sActivities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        sActivities.clear();
    }

}
