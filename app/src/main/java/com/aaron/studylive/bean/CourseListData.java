package com.aaron.studylive.bean;

/**
 * Created by recker on 16/5/24.
 *
 * 课程列表数据
 *
 */
public class CourseListData {

    private int id;
    private String name;  //课程名
    private String pic;  //封面图片
    private String thumb; //缩略图
    private String desc; //描述
    private int isLearned;//是否学习
    private int numbers;//学习人数
    private int numLession;  //课时数
    private long updateTime;//更新时间(时间戳)



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIsLearned() {
        return isLearned;
    }

    public void setIsLearned(int isLearned) {
        this.isLearned = isLearned;
    }


    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getNumLession() {
        return numLession;
    }

    public void setNumLession(int numLession) {
        this.numLession = numLession;
    }

    @Override
    public String toString() {
        return "CourseListData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", desc='" + desc + '\'' +
                ", isLearned=" + isLearned +
                ", numbers=" + numbers +
                ", updateTime=" + updateTime +
                '}';
    }
}
