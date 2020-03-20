package com.aaron.studylive.bean;

/**
 * Created by Aaron on 2020/3/20
 * The current project is StudyLive
 *
 * @Describe:  学生数据信息
 */
public class StudentInfo  {
    public long rowid;
    public int xuhao;
    public String name;   //昵称
    public String update_time;  //更新时间
    public String phone;  //手机号
    public String password;   //密码

    public StudentInfo() {
        rowid = 0L;
        xuhao = 0;
        name = "";
        update_time = "";
        phone = "";
        password = "";
    }
}
