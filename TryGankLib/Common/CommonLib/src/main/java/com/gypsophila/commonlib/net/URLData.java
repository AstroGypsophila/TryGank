package com.gypsophila.commonlib.net;

/**
 * Created by Gypsophila on 2016/7/31.
 * MobileApi接口信息实体
 */
public class URLData {

    private String key;
    private long expires; //数据缓存
    private String netType;
    private String url;
    private String mockClass;


    public URLData() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMockClass() {
        return mockClass;
    }

    public void setMockClass(String mockClass) {
        this.mockClass = mockClass;
    }
}
