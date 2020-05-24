package com.aaron.studylive.database;

/**
 * Created by Aaron on 2020/3/20
 * The current project is StudyLive
 *
 * @Describe:  学生数据信息(数据库)
 */
public class StudentInfo  {
    public long rowid; //0
    public String name;   //昵称
    public String update_time;  //更新时间
    public String phone;  //手机号
    public String username;
    public String password;
    public int roleId;   //角色Id 3：老师；2：学生
    public String email;
    public String create_time;


    public StudentInfo() {
        rowid = 0L;
        name = "";
        update_time = "";
        phone = "";
        username = "";
        email = "";
        create_time = "";
        roleId = 0;
    }
}
