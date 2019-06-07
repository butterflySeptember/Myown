package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class mysqlclass extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table book("+"id integer primary key autoincrement,"
            +"author text, "
            +"author, "
            +"price)";
    private final Context mcontext;


    public mysqlclass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext =  context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(mcontext,"create succeed",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
