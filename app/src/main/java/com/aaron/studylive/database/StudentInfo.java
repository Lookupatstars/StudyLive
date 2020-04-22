package com.aaron.studylive.database;

/**
 * Created by Aaron on 2020/3/20
 * The current project is StudyLive
 *
 * @Describe:  学生数据信息(数据库)
 */
public class StudentInfo  {
    public long rowid; //0
    public int permission;  //权限 5
    public String name;   //昵称 3
    public String update_time;  //更新时间 4
    public String phone;  //手机号 1
    public String password;   //密码 2
    public String teacherid;  //职工号 6

    public StudentInfo() {
        rowid = 0L;
        permission = 0;
        name = "";
        update_time = "";
        phone = "";
        password = "";
        teacherid = "";
    }
}
