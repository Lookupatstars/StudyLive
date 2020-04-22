package com.aaron.studylive.database;

/**
 * Created by Aaron on 2020/3/20
 * The current project is StudyLive
 *
 * @Describe:  学生数据信息(数据库)
 */
public class TeacherInfo {
    public long rowid; //0
    public int permission;  //权限 1
    public String name;   //昵称 2
    public String update_time;  //更新时间 3
    public String phone;  //手机号 4
    public String password;   //密码 5

    public TeacherInfo() {
        rowid = 0L;
        permission = 0;
        name = "";
        update_time = "";
        phone = "";
        password = "";
    }
}
