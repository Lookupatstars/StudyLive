package com.aaron.studylive.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aaron.studylive.utils.L;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/20
 * The current project is StudyLive
 *
 * @Describe:  用户数据库的操作
 */

@SuppressLint("DefaultLocale")
public class StudentDBhelper extends SQLiteOpenHelper {

    private static final String TAG = "StudentDBhelper";
    private static final String DB_NAME = "student.db";  //数据库的名称
    private static final int DB_VERSION = 1;  //数据库版本
    private static StudentDBhelper studentDBhelper = null;  //数据库帮助器的实例
    private SQLiteDatabase sDB = null;  //数据库的实例
    public static final String TABLE_NAME = "student_info";   //表的名称


    private StudentDBhelper(Context context){
        super(context , DB_NAME ,null , DB_VERSION);
    }

    private StudentDBhelper(Context context,int version){
        super(context , DB_NAME , null ,version);
    }

    //利用单例模式获取数据库帮助器
    public static StudentDBhelper getInstance(Context context, int version){
        if (version > 0 && studentDBhelper == null){
            studentDBhelper = new StudentDBhelper(context, version);
        }else if(studentDBhelper == null){
            studentDBhelper = new StudentDBhelper(context);
        }
        return studentDBhelper;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink(){
        if(sDB == null || !sDB.isOpen()){
            sDB = studentDBhelper.getReadableDatabase();
        }
        return sDB;
    }

    //打开数据库的写连接
    public SQLiteDatabase openWriteLink(){
        if(sDB == null || !sDB.isOpen()){
            sDB = studentDBhelper.openWriteLink();
        }
        return sDB;
    }

    //关闭数据库的连接
    public void closeLink(){
        if(sDB != null && sDB.isOpen()){
            sDB.close();
            sDB = null;
        }
    }

    //创建数据库，建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        L.d("onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        L.d("drop_sql",drop_sql);
        db.execSQL(drop_sql);

        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "phone VARCHAR NOT NULL," + "passwd INTEGER NOT NULL,"
                + "name LONG NOT NULL,"  + "update_time VARCHAR NOT NULL"
                + ");";
        L.d( "create_sql:",create_sql);
        db.execSQL(create_sql);

    }

    //修改数据库，执行表结构的变更语句
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion >1){
            //当数据库版本大于1时，更新表
        }
    }

    //删除一条指定条件的记录
    public int delete(String condition){
        // 执行删除记录动作，该语句返回删除记录的数目
        return sDB.delete(TABLE_NAME,condition,null);
    }

    //删除所有记录
    public int deleteAll(){
        // 执行删除记录动作，该语句返回删除记录的数目
        return sDB.delete(TABLE_NAME,"1=1",null);
    }

    // 往该表添加一条记录
    public long insert(UserInfo info) {
        ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
        infoArray.add(info);
        return insert(infoArray);
    }

    // 往该表添加多条记录
    public long insert(ArrayList<UserInfo> infoArray) {
        long result = -1;
        for (int i = 0; i < infoArray.size(); i++) {
            UserInfo info = infoArray.get(i);
            ArrayList<UserInfo> tempArray = new ArrayList<UserInfo>();
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            if (info.name != null && info.name.length() > 0) {
                String condition = String.format("name='%s'", info.name);
                tempArray = query(condition);
                if (tempArray.size() > 0) {
                    update(info, condition);
                    result = tempArray.get(0).rowid;
                    continue;
                }
            }
            // 如果存在同样的手机号码，则更新记录
            if (info.phone != null && info.phone.length() > 0) {
                String condition = String.format("phone='%s'", info.phone);
                tempArray = query(condition);
                if (tempArray.size() > 0) {
                    update(info, condition);
                    result = tempArray.get(0).rowid;
                    continue;
                }
            }
            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();
            cv.put("name", info.name);
            cv.put("age", info.age);
            cv.put("height", info.height);
            cv.put("weight", info.weight);
            cv.put("married", info.married);
            cv.put("update_time", info.update_time);
            cv.put("phone", info.phone);
            cv.put("password", info.password);
            // 执行插入记录动作，该语句返回插入记录的行号
            result = mDB.insert(TABLE_NAME, "", cv);
            // 添加成功后返回行号，失败后返回-1
            if (result == -1) {
                return result;
            }
        }
        return result;
    }



}
