package com.aaron.studylive.utils;

/**
 * Created by Aaron on 2020/4/6
 * The current project is StudyLive
 *
 * @Describe:  http 网络调用的回调监听机制
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
