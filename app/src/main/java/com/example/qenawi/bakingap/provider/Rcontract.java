package com.example.qenawi.bakingap.provider;

import android.net.Uri;

/**
 * Created by QEnawi on 7/5/2017.
 */

public class Rcontract
{
    public  static  final String ProviderName="com.example.qenawi.bakingap";//my app packge name autority
    public  static  final Uri BaseContentUri=Uri.parse("content://"+ProviderName);
    public  static  final  String const_path="data";
    public static final Uri CONTENT_URI= BaseContentUri.buildUpon().appendPath(const_path).build();
    //dp
    public static final String INGREDIENT_NAME = "ingredientsName";
    public static final String RECIPE_NAME = "recipeName";
    public static final String TABLE_NAME = "bakeing";
    public static final String RECIPE_TAG = "TAG";
    public static String B_id="ID";
}
