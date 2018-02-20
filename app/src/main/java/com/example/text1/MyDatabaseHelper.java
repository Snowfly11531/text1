package com.example.text1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by hasee on 2017/5/25.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
     public static final String CREATE_YONGHU="create table yonghu("
             +"id integer primary key autoincrement,"
             +"name text,"
             +"password text,"
             +"phone text)";
    private Context mcontext;
    public MyDatabaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_YONGHU);
        Toast.makeText(mcontext,"create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        onCreate(db);
    }
}
