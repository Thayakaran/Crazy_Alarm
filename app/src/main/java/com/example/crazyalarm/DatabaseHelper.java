package com.example.crazyalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String Database = "AlarmSystem.db";
    public static final String Table = "alarm_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "TONE";
    public static final String COL_5 = "COUNT";
    public static final String COL_6 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context,Database,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+Table+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TIME TEXT,TONE TEXT,COUNT TEXT,STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table);
        onCreate(db);
    }

    public boolean insertData(String name, String time, String tone, String count, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, tone);
        contentValues.put(COL_5, count);
        contentValues.put(COL_6,status);
        long result = db.insert(Table,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table, null);
        return res;
    }

    public Cursor getAllOnAlarms(String status){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from alarm_table where STATUS = ? ", new String[] {status});
        return res;
    }

    public Integer deleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table, "ID = ?", new String[] {id});
    }

    public boolean updateAlarmStatus(String id , String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,status);
        int result = db.update(Table , contentValues,"ID = ?",new String[] {id});
        if(result > 0){
            return true;
        }
        else {
            return false;
        }

    }

    public boolean updateAlarm(String id , String name, String time, String tone, String count,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, tone);
        contentValues.put(COL_5, count);
        contentValues.put(COL_6,status);
        int result = db.update(Table , contentValues,"ID = ?",new String[] {id});
        if(result > 0){
            return true;
        }
        else {
            return false;
        }

    }

}
