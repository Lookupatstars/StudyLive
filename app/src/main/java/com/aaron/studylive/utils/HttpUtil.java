package com.aaron.studylive.utils;

import org.xutils.http.annotation.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aaron on 2020/4/6
 * The current project is StudyLive
 *
 * @Describe: 网络操作的工具类
 *
 * 调用时 String address = "  http://xxx"
 *  HttpUtil.sendHttpRequest(address){}
 *
 */
public class HttpUtil {


    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");  //POST 方式都要大写
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null ){
                        response.append(line);
                    }
                    if (listener != null){
                        //回调 onFinish()方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e){
                    if (listener != null){
                        //回调 onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
                //End try {} catch() {} finally {}
            }
        }).start();


    }

}
