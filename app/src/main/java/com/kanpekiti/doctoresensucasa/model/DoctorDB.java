package com.kanpekiti.doctoresensucasa.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DoctorDB extends SQLiteOpenHelper {


    public static String databaseName = "doctorDB.db";
    public static SQLiteDatabase.CursorFactory databaseFactory = null;
    public static int databaseVersion = 2;

    public DoctorDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE UserLogged(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, nombre TEXT, apellido TEXT, token TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS UserLogged");
        }
    }
}
