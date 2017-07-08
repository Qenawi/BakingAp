package com.example.qenawi.bakingap.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by QEnawi on 7/5/2017.
 */

public class RdbHelper extends SQLiteOpenHelper
{
    private  static  final String DataBaseName="bake.db";
    private  static  final  int DBV=1;
    private static  final  String ubgraden=
            "ALTER TABLE "
                    + Rcontract.TABLE_NAME + " ADD COLUMN " + "null" + " string;";
    public  static  final String quire=
            "CREATE TABLE " + Rcontract.TABLE_NAME + " (" +
                    Rcontract.B_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                   Rcontract.INGREDIENT_NAME + " VARCHAR(20)," +
                    Rcontract.RECIPE_NAME + " VARCHAR(20),"+
                    Rcontract.RECIPE_TAG + " VARCHAR(20) "
                    +")";


    public RdbHelper(Context context)
    {
        super(context, DataBaseName, null,DBV);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
sqLiteDatabase.execSQL(quire);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        if(oldVersion<2){/* ad required column */}

    }
}
