package com.aaron.studylive.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aaron on 2020/4/25
 * The current project is StudyLive
 *
 * @Describe: 数据请求  request
 */

public class HttpRequest {

    private static int connectTimeout = 5;
    private static int readTimeout = 5;

    private OkHttpClient client;

    private static HttpRequest instance;


    private HttpRequest() {
        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }


    public String GET(Context ctx, String url, Map<String, String> params) {
        //首先从SharedPreferences中获取sessionid
        SharedPreferences share =  ctx.getSharedPreferences("Session",MODE_PRIVATE);
        String sessionid= share.getString("sessionid","null");

        StringBuffer sb = new StringBuffer();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            sb.append("?");

            for (Map.Entry<String, String> entry : entrySet) {
                sb.append(entry.getKey());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        //重点：把sessionid加入到请求头
        Request request = new Request.Builder().url(url+sb.toString()).addHeader("cookie",sessionid).get().build();
        Call call = client.newCall(request);

        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String POST(Context ctx,String url, Map<String, String> params) {
        //首先从SharedPreferences中获取sessionid
        SharedPreferences share =  ctx.getSharedPreferences("Session",MODE_PRIVATE);
        String sessionid= share.getString("sessionid","null");

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        //重点：把sessionid加入到请求头
        Request request = new Request.Builder().url(url).addHeader("cookie",sessionid).post(formBodyBuilder.build()).build();
        Call call = client.newCall(request);

        try {
            return  call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
