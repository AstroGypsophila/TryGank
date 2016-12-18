package com.gypsophila.trygank.umeng;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Description：umeng 统计
 * Author：AstroGypsophila
 * GitHub：https://github.com/AstroGypsophila
 * Date：2016/12/9
 */
public class UmengEvent {

    public static final boolean DEBUG = true;

    public static void init() {
        if (DEBUG) {
            MobclickAgent.setDebugMode(true);
        }
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * 在每个activity onResume中调用
     *
     * @param ctx
     */
    public static void onResume(Context ctx) {
        if (ctx instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) ctx;
            MobclickAgent.onPageStart(activity.getClass().getSimpleName());
        }
        MobclickAgent.onResume(ctx);
    }

    /**
     * 在每个activity onPause中调用
     *
     * @param ctx
     */
    public static void onPause(Context ctx) {
        if (ctx instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) ctx;
            MobclickAgent.onPageEnd(activity.getClass().getSimpleName());
        }
        MobclickAgent.onPause(ctx);
    }

    public static void onFragmentStart(String fragmentTag) {
        MobclickAgent.onPageStart(fragmentTag);
    }

    public static void onFragmentEnd(String fragmentTag) {
        MobclickAgent.onPageEnd(fragmentTag);
    }

}
