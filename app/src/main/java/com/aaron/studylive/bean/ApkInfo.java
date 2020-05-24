package com.aaron.studylive.bean;

public class ApkInfo {
    public String file_name; //文件名  apk的名字
    public String package_name; //包名
    public int version_code;    //versionCode
    public String file_path;    //文件路径
    public int file_size;
    public String version_name; //versionName

    public ApkInfo() {
        file_name = "";
        package_name = "";
        version_code = 0;
        file_path = "";
        file_size = 0;
        version_name = "";
    }

    public ApkInfo(String title, String path, int size, String pkg_name, String vs_name, int vs_code) {
        file_name = title;
        package_name = pkg_name;
        version_code = vs_code;
        file_path = path;
        file_size = size;
        version_name = vs_name;
    }

}
