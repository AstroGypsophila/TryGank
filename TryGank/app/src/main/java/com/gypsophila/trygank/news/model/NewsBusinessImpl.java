package com.gypsophila.trygank.news.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.engine.AppConstants;
import com.gypsophila.trygank.engine.RemoteService;
import com.gypsophila.trygank.news.NewsJsonUtils;
import com.gypsophila.trygank.news.view.NewsFragment;

import java.util.List;

/**
 * Description : 新闻业务类，应在此处就完成数据解析
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsBusinessImpl implements INewsBusiness {

    @Override
    public void loadNews(BaseActivity activity,
                         String url,
                         final int type,
                         List<RequestParameter> params,
                         final NewsLoadListener listener,
                         boolean forceUpdate) {
        //将解析的数据传回presenter层
        RequestCallback callBack = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(content, getID(type));
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void onFail(String errorMessage) {
                listener.onFail(errorMessage);
            }
        };
        RemoteService.getInstance().invoke(activity, url, params, callBack, true);
    }


    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = AppConstants.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = AppConstants.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = AppConstants.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = AppConstants.JOKE_ID;
                break;
            default:
                id = AppConstants.TOP_ID;
                break;
        }
        return id;
    }

}
