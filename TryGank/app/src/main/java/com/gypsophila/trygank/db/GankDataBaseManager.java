package com.gypsophila.trygank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public class GankDataBaseManager {

    public static final String TABLE_NAME = "gank_article";
    public static final String GANKID = "gankid";
    public static final String GANKDESC = "gankdesc";
    public static final String GANKURL = "gankurl";
    public static final String GANKWHO = "gankwho";
    public static final String GANKTYPE = "ganktype";
    public static final String GANKTIME = "ganktime";

    public static synchronized int updateOrInsertGank(Context ctx, GankBean gankBean) {
        SQLiteDatabase db = null;
        int re = -1;
        GankDataBaseHelper helper = GankDataBaseHelper.getInstance(ctx);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GANKID, gankBean.id);
        values.put(GANKDESC, gankBean.desc);
        values.put(GANKURL, gankBean.url);
        values.put(GANKWHO, gankBean.who);
        values.put(GANKTYPE, gankBean.type);
        values.put(GANKTIME, DateUtil.DateToString(gankBean.publishTime));

        String[] whereArgs = new String[]{gankBean.id};
        re = db.update(TABLE_NAME, values, GANKID + "=?", whereArgs);
        if (re <= 1) {
            re = (int) db.insert(TABLE_NAME, null, values);
        }
        return re;
    }

    public static List<GankBean> getGankList(Context ctx) {
        List<GankBean> gankBeanList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            GankDataBaseHelper helper = GankDataBaseHelper.getInstance(ctx);
            db = helper.getReadableDatabase();
            cursor = db.query(TABLE_NAME, null, null, null, null, null, "_id desc");
            while (cursor.moveToNext()) {
                GankBean gankBean = new GankBean();
                gankBean.id = cursor.getString(cursor.getColumnIndex(GANKID));
                gankBean.desc = cursor.getString(cursor.getColumnIndex(GANKDESC));
                gankBean.url = cursor.getString(cursor.getColumnIndex(GANKURL));
                gankBean.who = cursor.getString(cursor.getColumnIndex(GANKWHO));
                gankBean.type = cursor.getString(cursor.getColumnIndex(GANKTYPE));
                Date date = DateUtil.StringToDate(cursor.getString(cursor.getColumnIndex(GANKTIME)));
                gankBean.publishTime = date;
                gankBeanList.add(gankBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return gankBeanList;

    }

    public static GankBean getGankBean(Context ctx, String gankId) {
        GankBean gankBean = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            GankDataBaseHelper helper = GankDataBaseHelper.getInstance(ctx);
            db = helper.getReadableDatabase();
            String[] whereArgs = new String[]{gankId};
            cursor = db.query(TABLE_NAME, null, GANKID + "=?", whereArgs, null, null, null);
            if (cursor.moveToNext()) {
                gankBean = new GankBean();
                gankBean.id = cursor.getString(cursor.getColumnIndex(GANKID));
                gankBean.desc = cursor.getString(cursor.getColumnIndex(GANKDESC));
                gankBean.url = cursor.getString(cursor.getColumnIndex(GANKURL));
                gankBean.who = cursor.getString(cursor.getColumnIndex(GANKWHO));
                gankBean.type = cursor.getString(cursor.getColumnIndex(GANKTYPE));
                Date date = DateUtil.StringToDate(cursor.getString(cursor.getColumnIndex(GANKTIME)));
                gankBean.publishTime = date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return gankBean;
    }

    public static int deleteGankBean(Context ctx, String gankId) {
        SQLiteDatabase db = null;
        int re = -1;
        try {
            GankDataBaseHelper helper = GankDataBaseHelper.getInstance(ctx);
            db = helper.getWritableDatabase();
            String[] whereArgs = new String[]{gankId};
            re = db.delete(TABLE_NAME, GANKID + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return re;
    }
}
