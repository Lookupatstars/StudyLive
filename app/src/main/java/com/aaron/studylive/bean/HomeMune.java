package com.aaron.studylive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2020/4/4
 * The current project is StudyLive
 *
 * @Describe:  分类信息的封装  ——  主界面的CourseWeb类
 */
public class HomeMune  {

    /**
     * code : 0
     * content : [{"create_time":"2017-01-04 21:10:27","id":1,"name":"前端开发","pid":0,"status":1,"type":1,"update_time":"2017-02-06 09:41:14"},{"create_time":"2017-01-04 21:10:37","id":2,"name":"后端开发","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:10:44","id":3,"name":"移动开发","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:10:50","id":4,"name":"数据库","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:09","id":5,"name":"云计算&大数据","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:23","id":6,"name":"运维&测试","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:35","id":7,"name":"视觉设计","pid":0,"status":1,"type":1},{"create_time":"2017-09-27 10:58:50","id":40,"name":"报表","pid":0,"status":1,"type":1},{"create_time":"2017-10-02 20:57:41","id":43,"name":"人工智能","pid":0,"status":1,"type":1,"update_time":"2017-10-02 20:57:56"},{"create_time":"2018-10-17 16:36:45","id":44,"name":"智能硬件","pid":0,"status":1,"type":1}]
     */
    //Gson解析数据书写技巧
    //逢{}创建对象，[]创建集合ArrayList
    //解析名字要一致

    public int code;
    public ArrayList<HomeMenuData> content;

    //内容的对象
    public class HomeMenuData{
        public String create_time;
        public int id;
        public String name;
        public int pid;
        public int status;
        public int type;
        public String update_time;

        @Override
        public String toString() {
            return "HomeMenuData{" +
                    "create_time='" + create_time + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", pid=" + pid +
                    ", status=" + status +
                    ", type=" + type +
                    ", update_time='" + update_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeMune{" +
                "code=" + code +
                ", content=" + content +
                '}';
    }
}
