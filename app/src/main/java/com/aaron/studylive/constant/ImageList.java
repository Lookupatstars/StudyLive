package com.aaron.studylive.constant;

import com.aaron.studylive.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/7
 * The current project is StudyLive
 *
 * @Describe: 轮播图的图片资源
 */
public class ImageList {
    public static ArrayList<Integer> getDefault() {
//        ArrayList<Integer> imageList = new ArrayList<Integer>();
//        imageList.add(R.drawable.banner_1);
//        imageList.add(R.drawable.banner_2);
//        imageList.add(R.drawable.banner_3);
//        imageList.add(R.drawable.banner_4);
//        imageList.add(R.drawable.banner_5);
//        return imageList;

        //使用背景替代
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        imageList.add(0xffff0000);
        imageList.add(0xffff0000);
        imageList.add(0xffff0000);
        imageList.add(0xffff0000);
        imageList.add(0xffff0000);

        return imageList;

    }
}
