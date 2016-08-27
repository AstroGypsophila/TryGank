package com.gypsophila.trygank.news.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.engine.RemoteService;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsBusinessImpl implements INewsBusiness {

    @Override
    public void loadNews(BaseActivity activity,
                         String apiKey,
                         List<RequestParameter> params,
                         RequestCallback callBack,
                         boolean forceUpdate) {
        RemoteService.getInstance().invoke(activity, apiKey, params, callBack, true);
    }

}
