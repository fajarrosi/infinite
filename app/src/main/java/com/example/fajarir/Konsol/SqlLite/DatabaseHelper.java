package com.example.fajarir.Konsol.SqlLite;

/**
 * Created by X550Z FX on 28/08/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fajarir.Konsol.model.ReminderModel;

import java.util.ArrayList;

/**
 * Created by X550Z FX on 26/08/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME ="Reminder.db";
    public static  final String TABLE_NAME ="reminder_table";
    public static  final String COL_1 ="ID";
    public static  final String COL_2 ="TITLE";
    public static  final String COL_3 ="DESCRIPTION";
    public static  final String COL_4 ="DATE_ACT";
    public static  final String COL_5 ="TIME_ACT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, DATE_ACT TEXT, TIME_ACT TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String title, String descAct, String dateAct, String timeAct){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, descAct);
        contentValues.put(COL_4, dateAct);
        contentValues.put(COL_5, timeAct);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }
    public ArrayList<ReminderModel> getData(){
        ArrayList<ReminderModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);
        StringBuffer stringBuffer = new StringBuffer();
        ReminderModel reminderModel = null;
        while (cursor.moveToNext()){
            reminderModel = new ReminderModel();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String desAct = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
            String dateAct = cursor.getString(cursor.getColumnIndexOrThrow("DATE_ACT"));
            String timeAct = cursor.getString(cursor.getColumnIndexOrThrow("TIME_ACT"));

            reminderModel.setTitle(title);
            reminderModel.setDescription(desAct);
            reminderModel.setDate(dateAct);
            reminderModel.setTime(timeAct);

            data.add(reminderModel);
        }
//        for (ReminderModel mo:data){
//            Log.i("Hello",""+mo.getTitle());
//        }
        return data;
    }
}
