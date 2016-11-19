package com.gypsophila.commonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public class PreferenceUtils {

    public static String APP_FILE_NAME = "APP_FILE_NAME";

    public static long getLong(Context ctx, String key, long defValue) {
        if (ctx == null) {
            return defValue;
        }
        return getSharedPreferences(ctx).getLong(key, defValue);
    }

    public static void putLong(Context ctx, String key, long value) {
        getSharedPreferences(ctx).edit().putLong(key, value).commit();
    }

    public static int getInteger(Context ctx, String key, int defValue) {
        if (ctx == null) {
            return defValue;
        }
        return getSharedPreferences(ctx).getInt(key, defValue);
    }

    public static void putInteger(Context ctx, String key, int value) {
        getSharedPreferences(ctx).edit().putInt(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (ctx == null) {
            return defValue;
        }
        return getSharedPreferences(ctx).getBoolean(key, defValue);
    }

    public static void putBoolean(Context ctx, String key, boolean value) {
        getSharedPreferences(ctx).edit().putBoolean(key, value).commit();
    }

    public static float getFloat(Context ctx, String key, float defValue) {
        if (ctx == null) {
            return defValue;
        }
        return getSharedPreferences(ctx).getFloat(key, defValue);
    }

    public static void putFloat(Context ctx, String key, float value) {
        getSharedPreferences(ctx).edit().putFloat(key, value).commit();
    }

    public static void putString(Context ctx, String key, String value) {
        getSharedPreferences(ctx).edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defValue) {
        if (ctx == null) {
            return defValue;
        }
        return getSharedPreferences(ctx).getString(key, defValue);
    }

    public static String getString(Context ctx, String key) {
        if (ctx == null) {
            return "";
        }
        return getSharedPreferences(ctx).getString(key, "");
    }

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(APP_FILE_NAME, Activity.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences(Context ctx, String filename) {
        return ctx.getSharedPreferences(filename, Activity.MODE_PRIVATE);
    }
}
