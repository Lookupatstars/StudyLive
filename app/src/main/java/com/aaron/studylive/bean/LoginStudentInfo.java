package com.aaron.studylive.bean;

/**
 * Created by Aaron on 2020/4/17
 * The current project is StudyLive
 *
 * @Describe: 登录信息
 */
public class LoginStudentInfo  {
    static private int sId;
    static private String name;
    static private String update_time;  //更新时间 3
    static private String phone;  //手机号 4
    static private int permission;  //权限 1=学生  2=老师
    static private String teacherid; //职工号

    public int getsId() {
        return sId;
    }

    public String getName() {
        return name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getPhone() {
        return phone;
    }

    public static int getPermission() {
        return permission;
    }

    public static String getTeacherid() {
        return teacherid;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static void setPermission(int permission) {
        LoginStudentInfo.permission = permission;
    }

    public static void setTeacherid(String teacherid) {
        LoginStudentInfo.teacherid = teacherid;
    }

    @Override
    public String toString() {
        return "LoginStudentInfo{" +
                "sId=" + sId +
                ", name='" + name + '\'' +
                ", update_time='" + update_time + '\'' +
                ", phone='" + phone + '\'' +
                ",permission="+ permission +
                ", teacherid='" + teacherid + '\'' +
                '}';
    }

}
