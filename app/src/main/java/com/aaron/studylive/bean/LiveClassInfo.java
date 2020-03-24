package com.aaron.studylive.bean;

import com.aaron.studylive.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/24
 * The current project is StudyLive
 *
 * @Describe: 直播公开课的信息
 */
public class LiveClassInfo {
    public int live_class_item_id;
    public String live_class_teacher;
    public String live_class_title;
    public String live_class_time;
    public String live_class_name;
    public String desc;
    public boolean bPressed;
    public int id;
    private static int seq = 0;

    public LiveClassInfo(int live_class_item_id, String live_class_title,String live_class_teacher,String live_class_name,String live_class_time, String desc) {
        this.live_class_item_id = live_class_item_id;
        this.live_class_title = live_class_title;
        this.live_class_teacher = live_class_teacher;
        this.live_class_name = live_class_name;
        this.live_class_time = live_class_time;

        this.desc = desc;
        this.bPressed = false;
        this.id = this.seq;
        this.seq++;
    }

    private static int[] liveClassTeacherHeadArray = {
            R.drawable.teacher1,R.drawable.teacher2,R.drawable.teacher3,
            R.drawable.teacher4,R.drawable.teacher5,R.drawable.teacher6
    };
    private static String[] liveClassTitleArray = {
            "Android: 直播APP的设计与实现","MySQL: 数据库的创建以及增删改查","研究生复试的技巧",
            "浅谈机器学习","英语在日常生活中的重要性","微信公众号的开发"
    };
    private static String[] liveClassTeacherNameArray = {
            "赵永志","赵丽敏","张文才",
            "颜巧巧","刘惠萍","郑浩然"
    };
    private static String[] liveClassNameArray = {
            "Android","MySQL","考研",
            "机器学习","英语","软件开发"
    };
    private static String[] liveClassTime = {
            "2020.06.01 09:00-11:00","2020.06.01 14:00-16:00","2020.06.03 10:00-11:30",
            "2020.06.04 11:00-12:00","2020.06.04 15:00-17:00","2020.06.05 09:00-11:00"
    };

    public static ArrayList<LiveClassInfo> getDefaultStag() {
        ArrayList<LiveClassInfo> stagArray = new ArrayList<LiveClassInfo>();
        for (int i = 0; i < liveClassTeacherHeadArray.length; i++) {
            stagArray.add(new LiveClassInfo(liveClassTeacherHeadArray[i], liveClassTitleArray[i],
                    liveClassTeacherNameArray[i],liveClassNameArray[i],liveClassTime[i],null));
        }
        return stagArray;
    }
}
