package com.aaron.studylive.bean;

import android.graphics.Bitmap;

/**
 * Created by Aaron on 2020/4/17
 * The current project is StudyLive
 *
 * @Describe: 登录信息
 * 保存查询到的登录信息，方便对比和后续的传递给主界面使用
 */
public class LoginData  {
    static private int sId;
    static private String username;  //登录名称
    static private String password;
    static private String email;
    static private int roleId;  //角色ID  2：学生 3：讲师
    static private String name;     //昵称
    static private Bitmap img;    //头像
    static private String phone;  //手机号
    static private String update_time;  //更新时间
    static private String create_time;

    public static int getsId() {
        return sId;
    }

    public static String getCreate_time() {
        return create_time;
    }

    public static Bitmap getImg() {
        return img;
    }

    public static int getRoleId() {
        return roleId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public static String getUpdate_time() {
        return update_time;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setsId(int sId) {
        LoginData.sId = sId;
    }

    public static void setName(String name) {
        LoginData.name = name;
    }

    public static void setUpdate_time(String update_time) {
        LoginData.update_time = update_time;
    }

    public static void setPhone(String phone) {
        LoginData.phone = phone;
    }

    public static void setImg(Bitmap img) {
        LoginData.img = img;
    }

    public static void setRoleId(int roleId) {
        LoginData.roleId = roleId;
    }

    public static void setCreate_time(String create_time) {
        LoginData.create_time = create_time;
    }

    public static void setUsername(String username) {
        LoginData.username = username;
    }

    public static void setEmail(String email) {
        LoginData.email = email;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "sId=" + sId +
                ", name='" + name + '\'' +
                ", update_time='" + update_time + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
