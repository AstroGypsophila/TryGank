package com.gypsophila.trygank.business;


import android.app.Application;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.gypsophila.commonlib.cache.CacheManager;
import com.gypsophila.trygank.umeng.UmengEvent;

import java.util.HashMap;
import java.util.Map;

public class GankApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UmengEvent.init();
        CacheManager.getInstance().initCacheDir();
        initFeedback();
    }

    private void initFeedback() {
        //第二个参数是appkey，就是百川应用创建时候的appkey

        FeedbackAPI.initAnnoy(this, "23564214");
        Map<String, String> customMap = new HashMap<>();
        customMap.put("enableAudio", "0");
        FeedbackAPI.setUICustomInfo(customMap);
    }
}
