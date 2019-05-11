package com.example.seznamnakup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper    {

    public static final String DATABASE_NAME="mylist.db";
    public static final String TABLE_NAME="zbozi_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAZEV";


    public DatabaseHelper(Context context){super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAZEV TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String t = "DROP IF TABLE EXIST ";
        db.execSQL(t+TABLE_NAME);
    }

    public boolean AddData(String zbozi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL2, zbozi);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result ==-1)
 		    return false;
        else 
            return true;
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
    }

    public boolean updateZ(Item z) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL2, z.getNazev());
        long result = db.update(TABLE_NAME, contentValues, COL1 + " = ?",new String[]{String.valueOf(z.getId())});
        if(result ==-1)
            return false;
        else
            return true;
    }

    public void deleteZ(Item z) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL1 + " = ?",
                new String[]{String.valueOf(z.getId())});
    }
/*
    public void deleteZ(String nazev) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL2 + " = ?",
                new String[]{String.valueOf(nazev)});
    }*/

}
