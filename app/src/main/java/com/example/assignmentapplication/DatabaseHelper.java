package com.example.assignmentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //Database version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "form_database";
    //Database Table name
    private static final String TABLE_NAME = "form";
    //Table columns
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CLGNAME = "clgname";
    public static final String EMAIL = "email";
    public static final String NUMBER = "number";
    public static final String FILE = "file";
    private SQLiteDatabase sqLiteDatabase;


    //creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + CLGNAME + " TEXT NOT NULL,"+ NAME + " TEXT NOT NULL,"+EMAIL+" TEXT NOT NULL,"+NUMBER+" TEXT ,"+FILE+" TEXT );";
    //Constructor
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add Event Data
    public void addEvent(FormModel formModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, formModel.getName());
        contentValues.put(DatabaseHelper.EMAIL, formModel.getEmail());
        contentValues.put(DatabaseHelper.NUMBER, formModel.getNumber());
        contentValues.put(DatabaseHelper.FILE, formModel.getFile());
        contentValues.put(DatabaseHelper.CLGNAME, formModel.getClgname());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null,contentValues);
    }

    public ArrayList<FormModel> getFormList(){
        String sql = "select * from form ORDER BY id DESC";
       // String sql = "select * from " + TABLE_NAME + "ORDER BY id DESC";
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<FormModel> formModelArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(2);
                String email = cursor.getString(3);
                String mobile = cursor.getString(4);
                String file = cursor.getString(5);
                String clg = cursor.getString(1);
                formModelArrayList.add(new FormModel(id,name,email,mobile,file,clg));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return formModelArrayList;
    }

    public void deleteEvent(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});
    }

}
