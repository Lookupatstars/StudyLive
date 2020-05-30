package com.aaron.studylive.bean;

/**
 * Created by Aaron on 16/5/25.
 * The current project is StudyLive
 *
 * @Describe:
 */
public class ClassifyData {

    private int id;

    private String name;

    private int pid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "ClassifyData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numbers=" + pid +
                '}';
    }
}
