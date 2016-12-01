package com.gypsophila.trygank.business.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public class GankIOPlusBean extends GankBase {

    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android") public List<GankBean> androidList;
        @SerializedName("休息视频") public List<GankBean> videoList;
        @SerializedName("iOS") public List<GankBean> iOSList;
        @SerializedName("福利") public List<GankBean> welfareList;
        @SerializedName("拓展资源") public List<GankBean> extraList;
        @SerializedName("瞎推荐") public List<GankBean> recommendList;
        @SerializedName("App") public List<GankBean> appList;
    }
}
