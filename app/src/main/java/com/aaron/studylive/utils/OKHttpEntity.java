package com.aaron.studylive.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Aaron on 2020/5/23
 * The current project is StudyLive
 *
 * @Describe:
 */
public class OKHttpEntity {
    private static int connectTimeout = 5;
    private static int readTimeout = 5;

    private String url;
//    private OkHttpClient client;
    private Request request;
    private Callback callback;

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build();

    public static void GetRequest(final String url, Callback callback, String sessionKey){
        Request request = new Request.Builder()
                .header("cookie",sessionKey)
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
