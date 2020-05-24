package com.aaron.studylive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2020/5/20
 * The current project is StudyLive
 *
 * @Describe:  在评论界面中，再次获取的课程数据
 */
public class ClassInCommentData {

    public int current;
    public int pages;
    public int size;
    public int total;
    public ArrayList<CommentContentRecords> records;

    public static class CommentContentRecords {
        public String content;
        public int courseId;
        public String createTime;
        public int id;
        public int lessonId;
        public int pid;
        public int type;
        public int userId;
        public String img;
        public String name;
        public List<CommentContentRecordsReplays> replys;

        public CommentContentRecords(String name , String content, String time){
            this.name = name;
            this.content = content;
            this.createTime = time;
        }


        public List<CommentContentRecordsReplays> getReplys() {
            return replys;
        }

        public void setReplys(List<CommentContentRecordsReplays> replys) {
            this.replys = replys;
        }



        @Override
        public String toString() {
            return "CommentContentRecords{" +
                    "content='" + content + '\'' +
                    ", courseId=" + courseId +
                    ", createTime='" + createTime + '\'' +
                    ", id=" + id +
                    ", lessonId=" + lessonId +
                    ", pid=" + pid +
                    ", type=" + type +
                    ", userId=" + userId +
                    ", name='" + name + '\'' +
                    ", replys=" + replys +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassInCommentData{" +
                "current=" + current +
                ", pages=" + pages +
                ", size=" + size +
                ", total=" + total +
                ", records=" + records +
                '}';
    }
}
