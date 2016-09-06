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

    public static final String END_URL = "-" + PAGE_SIZE + ".html";
}
