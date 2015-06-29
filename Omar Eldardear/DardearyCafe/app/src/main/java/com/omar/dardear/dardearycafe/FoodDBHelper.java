package com.omar.dardear.dardearycafe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Omar on 6/19/2015.
 */
public class FoodDBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "fooddb.sqlite";

    private static int DATABASE_VERSION = 1;


    public FoodDBHelper(Context C) {
        super(C, DATABASE_NAME, null, DATABASE_VERSION);
        C.openOrCreateDatabase(DATABASE_NAME,C.MODE_PRIVATE,null);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { }


//
//
//    public ArrayList<deserts> GetAllDeserts(){
//        ArrayList<deserts> _DataList=new ArrayList<deserts>();
//        String selectQuery = "SELECT * FROM " + TABLE_DESERTS;
//        Cursor cu = DB_READABLE.rawQuery(selectQuery, null);
//        if (cu.moveToFirst()) {
//            do {
//                deserts C = new
// deserts();
//                C.ID = cu.getInt(0);
//                C.Name = cu.getString(2);
//
//            } while (cu.moveToNext());
//        }
//        return _DataList;
//    }
}



