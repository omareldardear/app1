package com.omar.dardear.dardearycafe;

import android.net.Uri;

/**
 * Created by Omar on 6/22/2015.
 */
public class FoodContract {


    public static final String CONTENT_AUTHORITY = "com.omar.dardear.dardearycafe";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_main_dishs="MainDishs";
    public static final String PATH_appetizers="Appetizers";
    public static final String PATH_pizza="Pizza";
    public static final String PATH_pasta_rice="PastaRice";
    public static final String PATH_deserts="Deserts";
    public static final String PATH_drinks="Drinks";




}
