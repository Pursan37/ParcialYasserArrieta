package com.example.parcialyasserarrieta;


import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class BaseHelper extends SQLiteOpenHelper {
    String tabla="CREATE TABLE PERSONAS(ID INTEGER PRIMARY KEY, NOMBRE TEXT, TELEFONO TEXT, GRUPO TEXT)";
    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
