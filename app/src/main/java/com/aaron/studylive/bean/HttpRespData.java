package com.aaron.studylive.bean;

/**
 *  请求网络版本的返回数据
 */

public class HttpRespData {
    public String appId;
    public String appVersion;
    public String note;
    public String appUrl;
    public String fileSize;
    public String md5Val;

    public HttpRespData() {
        appVersion = "";
        note = "";
        appUrl = "";
        fileSize = "";
        md5Val = "";
    }
}

