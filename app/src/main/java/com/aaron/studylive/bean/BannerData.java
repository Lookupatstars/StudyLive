package com.aaron.studylive.bean;

/**
 * Created by recker on 16/5/24.
 *
 * 广告栏数据
 *
 */
public class BannerData {

    private int id;
    private int type;
    private int typeId;
    private String name;
    private String pic;
    private String links;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "id=" + id +
                ", type=" + type +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", links='" + links + '\'' +
                '}';
    }
}

/**
public class BannerData  {

    /**
     * code : 0
     * content : [{"create_time":"2017-01-04 21:10:27","id":1,"name":"前端开发","pid":0,"status":1,"type":1,"update_time":"2017-02-06 09:41:14"},{"create_time":"2017-01-04 21:10:37","id":2,"name":"后端开发","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:10:44","id":3,"name":"移动开发","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:10:50","id":4,"name":"数据库","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:09","id":5,"name":"云计算&大数据","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:23","id":6,"name":"运维&测试","pid":0,"status":1,"type":1},{"create_time":"2017-01-04 21:11:35","id":7,"name":"视觉设计","pid":0,"status":1,"type":1},{"create_time":"2017-09-27 10:58:50","id":40,"name":"报表","pid":0,"status":1,"type":1},{"create_time":"2017-10-02 20:57:41","id":43,"name":"人工智能","pid":0,"status":1,"type":1,"update_time":"2017-10-02 20:57:56"},{"create_time":"2018-10-17 16:36:45","id":44,"name":"智能硬件","pid":0,"status":1,"type":1}]
     */
/**
    //Gson解析数据书写技巧
    //逢{}创建对象，[]创建集合ArrayList
    //解析名字要一致

    public int code;
    public ArrayList<BannerDataContent> content;

    //内容的对象
    public class BannerDataContent{
        public String create_time;
        public int id;
        public String name;
        public int pid;
        public int status;
        public int type;
        public String update_time;

        @Override
        public String toString() {
            return "BannerDataContent{" +
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
        return "BannerData{" +
                "code=" + code +
                ", content=" + content +
                '}';
    }
}

*/