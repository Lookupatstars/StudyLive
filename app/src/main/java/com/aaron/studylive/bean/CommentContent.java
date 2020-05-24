package com.aaron.studylive.bean;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/5/24
 * The current project is StudyLive
 *
 * @Describe:  评论+回复信息
 */
public class CommentContent {

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
        public ArrayList<CommentContentRecordsReplays> replys;

        public static class CommentContentRecordsReplays  {
            public String content="";
            public int courseId =0;
            public String createTime ="";
            public int id = 0;
            public int lessonId= 0;
            public int pid= 0;
            public int type= 0;
            public int userId= 0;
            public String name ="";
            public String img ="";

            public CommentContentRecordsReplays(String name, String replyContent) {
                this.name = name;
                this.content = replyContent;
            }

            @Override
            public String toString() {
                return "CommentContentRecordsReplays{" +
                        "content='" + content + '\'' +
                        ", courseId=" + courseId +
                        ", createTime='" + createTime + '\'' +
                        ", id=" + id +
                        ", lessonId=" + lessonId +
                        ", pid=" + pid +
                        ", type=" + type +
                        ", userId=" + userId +
                        ", name='" + name + '\'' +
                        '}';
            }
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
        return "CommentContent{" +
                "current=" + current +
                ", pages=" + pages +
                ", size=" + size +
                ", total=" + total +
                ", records=" + records +
                '}';
    }
}
