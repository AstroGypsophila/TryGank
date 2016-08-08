package com.gypsophila.trygank.engine;


import android.app.Application;

import com.gypsophila.commonlib.cache.CacheManager;

public class GankApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate(); 
		
		CacheManager.getInstance().initCacheDir();
	}
}
