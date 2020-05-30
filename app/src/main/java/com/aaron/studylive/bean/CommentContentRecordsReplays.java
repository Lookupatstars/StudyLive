package com.aaron.studylive.bean;

import android.graphics.Bitmap;

/**
 * Created by Aaron on 2020/5/24
 * The current project is StudyLive
 *
 * @Describe:  评论回复的数据
 */

public class CommentContentRecordsReplays{
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
    public Bitmap bm;

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
                ", bm=" + bm +
                '}';
    }
}

