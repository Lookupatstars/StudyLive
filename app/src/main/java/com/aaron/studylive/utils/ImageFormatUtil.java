package com.aaron.studylive.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Aaron on 2020/5/21
 * The current project is StudyLive
 *
 * 一下两行为把本地图片转化为bitmap格式
 * InputStream is = getResources().openRawResource(R.drawable.default_header);
 * Bitmap mBitmap = BitmapFactory.decodeStream(is);
 *
 * @Describe:  图片格式转换的工具类
 */
public class ImageFormatUtil {

    //把bitmap转化为Base64格式的字符串
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    //把Base64转化为Bitmap格式的图片
    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //把图片网址URL转换为Bitmap格式的图片
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            L.d("bm  = "+bm);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }


    public static Bitmap returnBitMap(final String url){
        final Bitmap[] bitmap = new Bitmap[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap[0];
    }

    /**
     * 自定义裁剪，根据第一个像素点(左上角)X和Y轴坐标和需要的宽高来裁剪
     * @param srcBitmap
     * @param firstPixelX
     * @param firstPixelY
     * @param needWidth
     * @param needHeight
     * @param recycleSrc
     * @return
     */
    public static Bitmap cropBitmapCustom(Bitmap srcBitmap, int firstPixelX, int firstPixelY, int needWidth, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight before h : "+srcBitmap.getHeight());

        if(firstPixelX + needWidth > srcBitmap.getWidth()){
            needWidth = srcBitmap.getWidth() - firstPixelX;
        }

        if(firstPixelY + needHeight > srcBitmap.getHeight()){
            needHeight = srcBitmap.getHeight() - firstPixelY;
        }

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, firstPixelX, firstPixelY, needWidth, needHeight);

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight after h : "+cropBitmap.getHeight());


        /**回收之前的Bitmap*/
//        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
//            GlideBitmapPool.putBitmap(srcBitmap);
//        }

        return cropBitmap;
    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    public static  Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
// 注意一定要用ARGB_8888，否则因为背景不透明导致遮罩失败
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
// 产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
// 首先绘制圆形
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
// 使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
// 绘制图片
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


}
