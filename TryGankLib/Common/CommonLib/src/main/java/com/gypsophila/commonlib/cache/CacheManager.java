package com.gypsophila.commonlib.cache;

import android.os.Environment;

import com.gypsophila.commonlib.utils.BaseUtils;

import java.io.File;

/**
 * Created by Gypsophila on 2016/8/2.
 * 缓存管理器
 */
public class CacheManager {
    /**
     * 缓存文件路径
     * 应灵活配置为应用名
     */
    public static final String APP_CACHE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/TryGank/appdata/";

    /**
     * sdcard最小空间，如果小于10M，不再向sdcard写入任何数据
     */
    public static final long SDCARD_MIN_SPACE = 1024 * 1024 * 10;

    private static CacheManager manager;

    /**
     * 获取CacheManager实例
     *
     * @return
     */
    public static CacheManager getInstance() {
        if (CacheManager.manager == null) {
            CacheManager.manager = new CacheManager();
        }
        return CacheManager.manager;
    }

    /**
     * 从文件缓存中读取缓存，没有则返回空
     * @param key
     * @return 缓存内容
     */
    public String getFileCache(final String key) {
        String md5Key = BaseUtils.getMd5(key);
        if (contains(md5Key)) {
            final CacheItem item = getFromCache(md5Key);
            if (item != null) {
                return item.getData();
            }
        }
        return null;
    }

    /**
     * API data 缓存到文件
     *
     * @param key
     * @param data
     * @param expireTime
     */
    public void putFileCache(final String key, final String data, final long expireTime) {
        String md5Key = BaseUtils.getMd5(key);
        final CacheItem item = new CacheItem(md5Key, data, expireTime);
        putIntoCache(item);
    }

    /**
     * 将CacheItem从磁盘中读取出来
     *
     * @param key md5文件名
     * @return 缓存数据CacheItem
     */
    synchronized CacheItem getFromCache(final String key) {
        CacheItem cacheItem = null;
        Object findItem = BaseUtils.restoreObject(APP_CACHE_PATH + key);
        if (cacheItem != null) {
            cacheItem = (CacheItem) findItem;
        }
        //缓存不存在
        if (cacheItem == null) {
            return null;
        }
        //缓存过期
        if (System.currentTimeMillis() > cacheItem.getTimeStamp()) {
            return null;
        }
        return cacheItem;
    }

    /**
     * 将CacheItem写入磁盘
     *
     * @param item
     * @return 是否缓存成功
     */
    synchronized boolean putIntoCache(final CacheItem item) {
        if (BaseUtils.sdcardMounted() && BaseUtils.getSDSize() > SDCARD_MIN_SPACE) {
            BaseUtils.saveObject(APP_CACHE_PATH + item.getKey(), item);
            return true;
        }
        return false;
    }

    /**
     * 清除缓存文件
     */
    void clearAllData() {
        File file = null;
        File[] files = null;
        file = new File(APP_CACHE_PATH);
        if (file.exists()) {
            files = file.listFiles();
            if (files != null) {
                for (final File f : files) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 查询是否有key对应的缓存文件
     *
     * @param key
     * @return
     */
    public boolean contains(final String key) {
        final File file = new File(APP_CACHE_PATH + key);
        return file.exists();
    }

    public void initCacheDir() {
        // sdcard已经挂载并且空间不小于10M，可以写入文件;小于10M时，清除缓存
        if (BaseUtils.sdcardMounted()) {
            if (BaseUtils.getSDSize() < SDCARD_MIN_SPACE) {
                clearAllData();
            } else {
                final File dir = new File(APP_CACHE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
        }
    }
}
