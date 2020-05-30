package com.aaron.studylive.bean;

/**
 * Created by Aaron on 2020/5/5
 * The current project is StudyLive
 *
 *
 *  请求网络版本的返回数据
 */

public class VersionData {
    public static String appId;
    public static String appVersion;
    public static String note;
    public static String appUrl;
    public static String fileSize;
    public static String md5Val;

    public VersionData() {
        appVersion = "";
        note = "";
        appUrl = "";
        fileSize = "";
        md5Val = "";
    }
}

