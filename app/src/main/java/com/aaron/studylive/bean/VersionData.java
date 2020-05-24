package com.aaron.studylive.bean;

/**
 * Created by Aaron on 2020/5/13
 * The current project is StudyLive
 *
 * @Describe:   服务器的版本信息
 */
public class VersionData {

    private String appVersion;
    private String note;
    private String appUrl;
    private String fileSize;
    private String releaseTime;

    public String getAppUrl() {
        return appUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getNote() {
        return note;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "VersionData{" +
                ", appVersion ='" + appVersion + '\'' +
                ", note=" + note + '\'' +
                ", appUrl=" + appUrl + '\''+
                ", fileSize=" + fileSize +'\''+
                ", releaseTime =" + releaseTime +'\''+
                '}';
    }

}
