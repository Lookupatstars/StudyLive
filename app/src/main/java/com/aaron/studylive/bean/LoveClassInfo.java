package com.aaron.studylive.bean;

import com.aaron.studylive.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/24
 * The current project is StudyLive
 *
 * @Describe: 猜你喜欢的课程的信息
 */

public class LoveClassInfo {
    public int love_class_image_id;
    public String love_class_teacher;
    public String love_class_title;
    public int love_class_hot;

    public String desc;
    public boolean bPressed;
    public int id;
    private static int seq = 0;

    public LoveClassInfo(int love_class_image_id, String love_class_title, String love_class_teacher, int love_class_hot, String desc) {
        this.love_class_image_id = love_class_image_id;
        this.love_class_title = love_class_title;
        this.love_class_teacher = love_class_teacher;
        this.love_class_hot = love_class_hot;

        this.desc = desc;
        this.bPressed = false;
        this.id = this.seq;
        this.seq++;
    }

    private static int[] loveClassImageArray = {
            R.drawable.skirt01,R.drawable.skirt02,R.drawable.skirt03,R.drawable.skirt04,R.drawable.skirt05,R.drawable.skirt06,R.drawable.skirt07,
            R.drawable.skirt08,R.drawable.skirt09,R.drawable.skirt10,R.drawable.skirt11,R.drawable.skirt12
    };
    private static String[] loveClassTitleArray = {
            "Android: 直播APP的设计与实现","MySQL: 数据库的创建以及增删改查，数据库的创建以及增删改查","研究生复试的技巧",
            "浅谈机器学习","英语在日常生活中的重要性","微信公众号的开发",
            "Android: 直播APP的设计与实现","MySQL: 数据库的创建以及增删改查，数据库的创建以及增删改查","研究生复试的技巧",
            "浅谈机器学习","英语在日常生活中的重要性","微信公众号的开发"
    };
    private static String[] loveClassTeacherNameArray = {
            "赵永志","赵丽敏","张文才",
            "颜巧巧","刘惠萍","郑浩然",
            "赵永志","赵丽敏","张文才",
            "颜巧巧","刘惠萍","郑浩然"
    };
    private static int[] loveClassHotArray = {
            Integer.parseInt("1231"), Integer.parseInt("12312"), Integer.parseInt("123"), Integer.parseInt("124"), Integer.parseInt("435"), Integer.parseInt("4656"),
            Integer.parseInt("25"), Integer.parseInt("23523"), Integer.parseInt("3486"), Integer.parseInt("3256"), Integer.parseInt("5362"), Integer.parseInt("2356")
    };

    public static ArrayList<LoveClassInfo> getDefaultStag() {
        ArrayList<LoveClassInfo> stagArray = new ArrayList<LoveClassInfo>();
        for (int i = 0; i < loveClassImageArray.length; i++) {
            stagArray.add(new LoveClassInfo(loveClassImageArray[i], loveClassTitleArray[i],
                    loveClassTeacherNameArray[i],loveClassHotArray[i],null));
        }
        return stagArray;
    }
}
