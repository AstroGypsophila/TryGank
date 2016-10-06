package com.gypsophila.trygank.business.news.model;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.news.view.NewsFragment;
import com.gypsophila.trygank.engine.AppConstants;
import com.gypsophila.trygank.engine.RemoteService;
import com.gypsophila.trygank.business.news.NewsJsonUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Description : 新闻业务类，应在此处就完成数据解析
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsBusinessImpl implements INewsBusiness {

    @Override
    public void loadNews(Context ctx,
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
                Logger.t("AstroGypsophila").e(errorMessage);
            }
        };
        if (ctx instanceof BaseActivity) {
            RemoteService.getInstance().invoke((BaseActivity) ctx, url, params, callBack, true);
        } else {
            RemoteService.getInstance().invoke( ctx, url, params, callBack, true);
        }
    }

    @Override
    public void loadNewsDetail(Context ctx, String url, final String docId, List<RequestParameter> params, final NewsDetailLoadListener listener, boolean forceUpdate) {
        RequestCallback callback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetail(content, docId);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFail(String errorMessage) {

            }
        };
        if (ctx instanceof BaseActivity) {
            RemoteService.getInstance().invoke((BaseActivity) ctx, url, params, callback, true);
        } else {
            RemoteService.getInstance().invoke(ctx, url, params, callback, true);
        }
    }


    /**
     * 获取ID
     *
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
