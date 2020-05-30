package com.aaron.studylive.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/24
 * The current project is StudyLive
 *
 * @Describe: 直播公开课的信息
 */
public class LiveClassData {

    public int total;
    public int size;
    public int current;
    public int pages;
    public ArrayList<LiveRecords>  records;

    public static class LiveRecords{
        public int id;
        public int userId;
        public String name;
        public String summary;
        public String startTime;
        public String endTime;
        public String replayPath;
        public String img2;
        public Bitmap bm;


        @Override
        public String toString() {
            return "LiveRecords{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", name='" + name + '\'' +
                    ", summary='" + summary + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", replayPath='" + replayPath + '\'' +
                    ", img2='" + img2 + '\'' +
                    ", bm=" + bm +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LiveClassData{" +
                "total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}
