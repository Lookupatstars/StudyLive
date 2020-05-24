package com.aaron.studylive.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aaron on 2020/5/19
 * The current project is StudyLive
 *
 * @Describe:   用来操作搜索查询的
 */
public class SearchRecordsDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "searchRecords.db";  //数据库的名称
    private static final int DB_VERSION = 1;  //数据库版本
    public static final String TABLE_NAME = "searchRecords_info";   //表的名称

    public SearchRecordsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(drop_sql);

        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "name VARCHAR NOT NULL"
                + ");";
        db.execSQL(create_sql);
    }

    //修改数据库，执行表结构的变更语句
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion >1){
            //当数据库版本大于1时，更新表
        }
    }

}
