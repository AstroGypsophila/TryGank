package com.gypsophila.trygank.engine;

public class AppConstants {

    //http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    //http://c.m.163.com/nc/article/list/T1350383429665/0-20.html

    public static final int PAGE_SIZE = 20;

    public static final String NeedCallback = "NeedCallback";

    public static final String CACHEDIR = "/data/data/com.youngheart/cache/";

    public static final String NETEASE_HOST = "http://c.m.163.com/";

    //头条
    public static final String TOP_URL = NETEASE_HOST + "nc/article/headline/";

    //新闻列表
    public static final String COMMON_URL = NETEASE_HOST + "nc/article/list/";

    public static final String GANK = "gank";
    //头条
    public static final String TOP_ID = "T1348647909107";
    // nba
    public static final String NBA_ID = "T1348649145984";
    // 汽车
    public static final String CAR_ID = "T1348654060988";
    // 笑话
    public static final String JOKE_ID = "T1350383429665";
    //分页
    public static final String END_URL = "-" + PAGE_SIZE + ".html";
    //新闻详情页前缀
    public static final String NEWS_DETAIL_URL = NETEASE_HOST + "nc/article/";
    public static final String NEWS_DETAIL_END_URL = "/full.html";

    public static final String GANK_HOST = "http://gank.io/api/";
    //http://gank.io/api/search/query/listview/category/Android/count/10/page/1
    //http://gank.io/api/history/content/day/2016/05/11
    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * <p>
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * 例：
     * http://gank.io/api/data/Android/10/1
     * http://gank.io/api/data/福利/10/1
     * http://gank.io/api/data/iOS/20/2
     * http://gank.io/api/data/all/20/2
     */
    public static final String GANK_TYPE_WELFARE = "福利";
    public static final String GANK_TYPE_IOS = "iOS";
    public static final String GANK_TYPE_ANDROID = "Android";
    public static final String GANK_TYPE_FRONT_END = "前端";
    public static final String GANK_TYPE_VIDEO = "休息视频";
}
