package com.aaron.studylive.bean;

/**
 * Created by Aaron on 2020/5/7
 * The current project is StudyLive
 *
 * @Describe:   等同于MediaData，只不过用于章节列表
 */
public class CpData {

    private int courseId;   //课程ID
    private String courseTime;  //时长（分钟）
    private String createTime;  //创建时间
    private int download;   //是否允许下载（1：允许 0：禁止）
    private int downloadCount;  //下载量
    private int id;
    private String name;  //课时名称
    private String resourceAddress;     //下载资源地址
    private String resourceAddress2;     //播放资源地址
    private int status;     //状态（0:待审核，1：已通过，2：未通过）
    private String summary;     //摘要
    private String updateTime;  //最后一次修改时间
    private int  userId;        //主表ID
    private int viewCount;      //观看量
    private int viewPermissions;    //观看权限（0：不限，1：注册用户）

    private boolean isSeleted = false;//是否被选中
    private boolean isSeletedEnd = false;//是否播放过

    public int getCourseId() {
        return courseId;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getDownload() {
        return download;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResourceAddress() {
        return resourceAddress;
    }

    public String getResourceAddress2() {
        return resourceAddress2;
    }

    public int getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getViewPermissions() {
        return viewPermissions;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

    public void setResourceAddress2(String resourceAddress2) {
        this.resourceAddress2 = resourceAddress2;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setViewPermissions(int viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public boolean isSeletedEnd() {
        return isSeletedEnd;
    }

    public void setSeletedEnd(boolean seletedEnd) {
        isSeletedEnd = seletedEnd;
    }

    @Override
    public String toString() {
        return "CpData{" +
                "courseId=" + courseId +
                ", courseTime='" + courseTime + '\'' +
                ", createTime=" + createTime + '\'' +
                ", download=" + download +
                ", downloadCount=" + downloadCount +
                ", id=" + id +
                ", name=" + name + '\'' +
                ", resourceAddress=" + resourceAddress + '\'' +
                ", resourceAddress2=" + resourceAddress2 + '\'' +
                ", status=" + status +
                ", summary='" + summary + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userId=" + userId +
                ", viewCount=" + viewCount +
                ", viewPermissions=" + viewPermissions +
                '}';
    }

    //    private Chapter chapter;
//
//    private List<MediaData> mediaDataList;
//
//    public Chapter getChapter() {
//        return chapter;
//    }
//
//    public void setChapter(Chapter chapter) {
//        this.chapter = chapter;
//    }
//
//    public List<MediaData> getMediaDataList() {
//        return mediaDataList;
//    }
//
//    public void setMediaDataList(List<MediaData> mediaDataList) {
//        this.mediaDataList = mediaDataList;
//    }
}
