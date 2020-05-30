package com.aaron.studylive.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 2020/4/25
 * The current project is StudyLive
 *
 * @Describe: 数据请求的 连接网站
 */
public class HttpUrl {

    private static HttpUrl instance;

    //主机地址
    private String mHostName = "http://218.28.238.170:8995";
//    private String mHostName = "http://course.zzu.gdatacloud.com:890/courseWeb/courseWeb";


    //Banner请求URL  Banner
    private String mBannerUrl = "http://course.zzu.gdatacloud.com:890/courseWeb/courseWeb/courseList?curr=1&directionId=0&sortId=0&typeId=0&way=1";


    //课程列表请求URL（最新）    http://218.28.238.170:8995/course/courses?current=1&orderBy=time&paging=true&size=10
    // http://course.zzu.gdatacloud.com:890/courseWeb/courseWeb/courseList?curr=1&directionId=0&sortId=0&typeId=0&way=1
    private String startNew = mHostName + "/course/courses?current=";
    private String endNew = "&orderBy=time&paging=true&size=10";
    private String mCourseListUrlNew;


    //课程列表请求URL（最热）    http://218.28.238.170:8995/course/courses?current=1&orderBy=hot&paging=true&size=10
    // http://course.zzu.gdatacloud.com:890/courseWeb/courseWeb/courseList?curr=1&directionId=0&sortId=0&typeId=0&way=1
    private String startHot = mHostName + "/course/courses?current=";
    private String endHot = "&orderBy=hot&paging=true&size=10";
    private String mCourseListUrlHot;


    //分类课程列表  // 第一层分类 http://218.28.238.170:8995/course/courseTypes?pid=0
    private String mClassifyListUrl =  "http://218.28.238.170:8995/course/courseTypes?pid=0";


    //获取课时信息   http://218.28.238.170:8995 /course/courseLesson/5/lessons?current=1&size=10
    private String mMediaInfoStart = mHostName + "/course/courseLesson/";
    private String mMediaInfoEnd = "/lessons?current=1&size=10";
    private String mMediaInfo;


    //获取推荐课程列表  http://218.28.238.170:8995/course/courses?current=1&orderBy=time&paging=true&size=100
    private String mRelevantCourse = mHostName + "/course/courses?current=1&orderBy=time&paging=true&size=1000";

    //版本更新
    //http://218.28.238.170:8995/sys/app/course98765/version
    private String lineVersion = "http://218.28.238.170:8995/sys/app/course98765/version";

    //登录
    //http://218.28.238.170:8995/user/userLogin/session
    private String loginUrl = "http://218.28.238.170:8995/user/userLogin/session";

    //注册
    //http://218.28.238.170:8995/user/userLogin/user
    private String register= "http://218.28.238.170:8995/user/userLogin/user";

    //展示评论 http://218.28.238.170:8995/course/courseComments/0/comments?current=1&paging=true&size=20
    private String startComment = mHostName + "/course/courseComments/";
    private String endComment = "/comments?current=1&paging=true&size=100";
    private String commentUrl ;

    //发评论和回复 http://218.28.238.170:8995/course/courseComments/784/comments
    //          http://218.28.238.170:8995/course/courseComments/37/comments
    private String startSendComment = mHostName +"/course/courseComments/";
    private String endSendComment = "/comments";
    private String sendComment;

    // 直播课 http://218.28.238.170:8995/live/liveInfos?current=1&paging=true&size=10
    private String liveInfo = "http://218.28.238.170:8995/live/liveInfos?current=1&paging=true&size=10";



    private HttpUrl() {

    }

    public static HttpUrl getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpUrl();
                }
            }
        }
        return instance;
    }

    public String getLiveInfo() {
        return liveInfo;
    }

    public String getSendComment(int lessonId) {
        sendComment = startSendComment +lessonId +endSendComment;
        return sendComment;
    }

    public String getCommentUrl(int lessonId) {
        commentUrl = startComment + lessonId + endComment;
        return commentUrl;
    }

    public String getRegister() {
        return register;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getLineVersion() {
        return lineVersion;
    }

    public String getBannerUrl() {
        return mBannerUrl;
    }

    public String getCourseListUrlNew(int current) {
        mCourseListUrlNew = startNew+ current + endNew;
        return mCourseListUrlNew;
    }

    public String getCourseListUrlHot(int current) {
        mCourseListUrlHot = startHot+ current + endHot;
        return mCourseListUrlHot;
    }

    public String getClassifListUrl() {
        return mClassifyListUrl;
    }


    public String getMediaInfo(int courseId) {
        mMediaInfo = mMediaInfoStart + courseId + mMediaInfoEnd;
        return mMediaInfo;
    }


    public String getRelevantCourse() {
        return mRelevantCourse;
    }

    /**
     * 获取广告轮参数
     * @return
     */
    public Map<String, String> getBannerParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("marking", "banner");
        params.put("token", "e2b26c9b138b1dba163470fb3379b774");

        return params;
    }

    //登录
    public Map<String,String> getLoginParams(){
        Map<String,String> params = new HashMap<>();
        params.put("password","111111");
        params.put("rememberMe","false");
        params.put("username","809011535@qq.com");
        return params;
    }


    /**
     * 获取课程列表参数 最热
     * @param page
     * @return
     */
//    public Map<String, String> getCourseListHotParams(int page) {
//        Map<String, String> params = new HashMap<>();
//        params.put("timestamp", System.currentTimeMillis()+"");
//        params.put("page", page+"");
//        params.put("uid", "2902109");
//        params.put("token", "933b40289b2a668bb882e2261a759267");
//
//        return params;
//    }

    /**
     * 获取课程分类列表参数
     * @return
     */
    public Map<String, String> getClassifyCourseParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("token", "0c383865823b8c7b9146b6e67b4e8b03");

        return params;
    }

    /**
     * 求职路线参数
     * @return
     */
    public Map<String, String> getJobLineParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("typeid", "1");
        params.put("token", "bcfc00be48e38434381aa1149b41e942");

        return params;
    }

    /**
     * 加薪利器参数
     * @param page
     * @param cid 标识(null代表全部， fe代表前段，be代表后端，mobile代表移动，fsd代表整站)
     * @return
     */
    public Map<String, String> getRaiseweaponParams(int page, String cid) {
        Map<String, String> params = new HashMap<>();

        if (cid != null)
            params.put("cid", cid);
        params.put("uid", "2902109");
        params.put("page", page+"");
        params.put("typeid", "2");
        params.put("token", "bcfc00be48e38434381aa1149b41e942");

        return params;
    }


    /**
     * 获取分类课程列表参数
     * @param type 1为全部, 2为初级, 3为中级，4为高级
     * @param page
     * @return
     */
    public Map<String, String> getClassifyListParams(int id, int type, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("cat_type", id+"");
        params.put("timestamp", System.currentTimeMillis()+"");
        params.put("uid", "2902109");
        params.put("page", page + "");
        params.put("all_type", "0");
        params.put("easy_type", type+"");
        params.put("token", "933b40289b2a668bb882e2261a759267");

        return params;
    }

    /**
     * 获取文章列表参数
     * @param id 0为全部, 105为前端开发，106为后端开发，107为职场/生活，109为设计
     *           110为移动开发，114为其它类干货
     * @param page
     * @return
     */
    public Map<String, String> getArticleListParams(String id, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("typeid", id);
        params.put("uid", "2902109");
        params.put("page", page + "");
        params.put("type", "0");
        params.put("aid", "0");
        params.put("token", "c0581c2f1a83e264a9bf1d74a0773ebd");

        return params;
    }

    /**
     * 获取文章内容参数
     * @param id
     * @return
     */
    public Map<String, String> getArticleContentParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("typeid", id);
        params.put("uid", "2902109");
        params.put("token", "2076cc47b14f0f8509e2cfd4358bb71e");

        return params;
    }

    /**
     * 获取视频信息参数
     * @param id
     * @return
     */
    public Map<String, String> getMediaInfoParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "ee292734814af1f4804f676c51e3337d");

        return params;
    }

    /**
     * 获取视频章节参数
     * @param id
     * @return
     */
    public Map<String, String> getCpInfoParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "56f37d20c0e34b9980b3cf910e35f342");

        return params;
    }

    /**
     * 获取课程评论列表参数
     * @param id
     * @param page
     * @return
     */
    public Map<String, String> getCourseCommentListParams(String id, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("page", page+"");
        params.put("token", "ec146a4a50030d232784e0e048827e36");

        return params;
    }

    /**
     * 获取课程简介参数
     * @param id
     * @return
     */
    public Map<String, String> getCourseIntroParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "54368c2b6bdb7bd22505ba6666ad5dad");

        return params;
    }

    /**
     * 获取推荐课程列表参数
     * @param id
     * @return
     */
    public Map<String, String> getRelevantCourseParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "88433c772ee11aa5eb997249b650e7c8");

        return params;
    }

}
