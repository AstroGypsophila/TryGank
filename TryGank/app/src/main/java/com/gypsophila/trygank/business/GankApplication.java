package com.gypsophila.trygank.business;


import android.app.Application;

import com.gypsophila.commonlib.cache.CacheManager;
import com.gypsophila.trygank.umeng.UmengEvent;

public class GankApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		UmengEvent.init();
		CacheManager.getInstance().initCacheDir();
	}
}
