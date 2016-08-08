package com.gypsophila.commonlib.cache;

import java.io.Serializable;

/**
 * Created by Gypsophila on 2016/8/2.
 */
public class CacheItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 存储的key */
    private final String key;

    /** JSON字符串 */
    private String data;

    /** 过期时间的时间戳 */
    private long timeStamp = 0L;

    public CacheItem(String key, String data, long timeStamp) {
        this.key = key;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
