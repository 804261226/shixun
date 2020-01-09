package com.example.administrator.car;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context) {
        super(context, "itcast.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE car(id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(20),price varchar(20),icon varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}