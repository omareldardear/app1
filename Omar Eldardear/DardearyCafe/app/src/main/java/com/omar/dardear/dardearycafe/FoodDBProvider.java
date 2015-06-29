package com.omar.dardear.dardearycafe;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Omar on 6/22/2015.
 */
public class FoodDBProvider extends ContentProvider {


    private FoodDBHelper foodDB;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static final String main_dishs_URI="content://com.omar.dardear.dardearycafe/MainDishes/2";
    public static final String appetizers_URI="content://com.omar.dardear.dardearycafe/Appetizers/1";
    public static final String pizza_URI="content://com.omar.dardear.dardearycafe/Pizza/4";
    public static final String pasta_rice_URI="content://com.omar.dardear.dardearycafe/PastaRice/3";
    public static final String deserts_URI="content://com.omar.dardear.dardearycafe/Deserts/6";
    public static final String drinks_URI="content://com.omar.dardear.dardearycafe/Drinks/5";


    private static String TABLE_APPETIZERS = "Appetizers";
    private static String TABLE_DESERTS = "Deserts";
    private static String TABLE_DRINKS = "Drinks";
    private static String TABLE_MAINDISHES = "MainDishes";
    private static String TABLE_Pizza = "Pizza";
    private static String TABLE_Pasta_Rice = "PastaRice";


    public static final int main_dishs=2;
    public static final int appetizers=1;
    public static final int pizza=4;
    public static final int pasta_rice=3;
    public static final int deserts=6;
    public static final int drinks=5;









    @Override
    public boolean onCreate()
    {

        foodDB=new FoodDBHelper(getContext());


        return true;
    }

    @Override
    public String getType(Uri uri)
    {

        return "abaa";
    }





    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        String selectQuery;
        switch (uri.toString()) {
            case main_dishs_URI: {
                selectQuery= "SELECT * FROM "+TABLE_MAINDISHES;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;
            }
            case appetizers_URI: {
                selectQuery= "SELECT * FROM "+TABLE_APPETIZERS;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;
            }
            case deserts_URI:
            {
                selectQuery= "SELECT * FROM "+TABLE_DESERTS;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;
            }
            case drinks_URI: {
                selectQuery= "SELECT * FROM "+TABLE_DRINKS;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;
            }
            case pasta_rice_URI: {
                selectQuery= "SELECT * FROM "+TABLE_Pasta_Rice;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;
            }
            case pizza_URI: {
                selectQuery= "SELECT * FROM "+TABLE_Pizza;
                retCursor=foodDB.getReadableDatabase().rawQuery(selectQuery,null);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return Uri.parse("ay 7aga");
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 1;
    }



    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 1;}







    private static UriMatcher buildUriMatcher()
    {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FoodContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, FoodContract.PATH_appetizers,appetizers);
        matcher.addURI(authority, FoodContract.PATH_deserts,deserts);
        matcher.addURI(authority, FoodContract.PATH_drinks,drinks);
        matcher.addURI(authority, FoodContract.PATH_main_dishs,main_dishs);
        matcher.addURI(authority, FoodContract.PATH_pasta_rice,pasta_rice);
        matcher.addURI(authority, FoodContract.PATH_pizza,pizza);

        return matcher;


    }


}








