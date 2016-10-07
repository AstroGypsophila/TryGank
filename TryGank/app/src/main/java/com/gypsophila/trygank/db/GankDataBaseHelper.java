package com.gypsophila.trygank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public class GankDataBaseHelper extends SQLiteOpenHelper {

    private static final String GANK_DATABASE_NAME = "Gank.db";
    private static final int DATABASE_VERSION = 1;

    private static GankDataBaseHelper sHelper = null;

    public static GankDataBaseHelper getInstance(Context ctx) {
        if (sHelper == null) {
            synchronized (GankDataBaseHelper.class) {
                if (sHelper == null) {
                    sHelper = new GankDataBaseHelper(ctx);
                }
            }
        }
        return sHelper;
    }

    private static final String GANK_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + GankDataBaseManager.TABLE_NAME + " (" +
            "_id Integer primary key autoincrement, " +
            GankDataBaseManager.GANKID + " text, " +
            GankDataBaseManager.GANKDESC + " text, " +
            GankDataBaseManager.GANKURL + " text, " +
            GankDataBaseManager.GANKWHO + " text, " +
            GankDataBaseManager.GANKTYPE + " text, " +
            GankDataBaseManager.GANKTIME + " text" +
            ")";

    public GankDataBaseHelper(Context context) {
        super(context, GANK_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GANK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
