package com.gypsophila.trygank.business;

import android.os.Environment;

public class AppConstants {

    public static final String NeedCallback = "NeedCallback";

    public static final String CACHEDIR = "/data/data/com.gypsophila.trygank/cache/";

    public static final String GANK = "gank";

    public static final String GANK_HOST = "http://gank.io/";

    public static final String GANK_XIANDU = GANK_HOST + "xiandu/";

    public static final String GANK_API = GANK_HOST + "api/";

    public static final String GANK_HISTORY = GANK_API + "day/history";

    public static final String GANK_DAY = GANK_API + "day/";

    public static int PAGE_SIZE = 20;

    public static String GITHUB = "https://github.com/AstroGypsophila/TryGank";


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

    public static String BASE_DIR = Environment.getExternalStorageDirectory() + "/trygank";
    public static String TMP_FILE = BASE_DIR + "/temp/";

    public static String THEME_VALUE = "THEME_VALUE";
}
