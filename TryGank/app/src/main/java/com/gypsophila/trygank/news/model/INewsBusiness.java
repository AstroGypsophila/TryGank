package com.gypsophila.trygank.news.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public interface INewsBusiness {

    void loadNews(BaseActivity activity,
                  String apiKey,
                  List<RequestParameter> params,
                  RequestCallback callBack,
                  boolean forceUpdate);
}
