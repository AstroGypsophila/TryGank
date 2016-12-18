package com.gypsophila.trygank.business.gank;

/**
 * Description：
 * Author：AstroGypsophila
 * GitHub：https://github.com/AstroGypsophila
 * Date：2016/12/18
 */
public enum FilterType {
    ALL(null),
    ANDROID("Android"),
    IOS("iOS"),
    FRONT_END("前端"),
    APP("App"),
    EXTRA("拓展资源"),
    RECOMMEND("瞎推荐");

    private String filter;

    FilterType(String filter) {
        this.filter = filter;
    }

    public static String getFilter(FilterType type) {
        return type.filter;
    }
}
