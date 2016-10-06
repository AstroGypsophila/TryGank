package com.gypsophila.trygank.business.news.model;

import android.content.Context;

import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public interface INewsBusiness {

    void loadNews(Context ctx,
                  String url,
                  int type,
                  List<RequestParameter> params,
                  NewsLoadListener listener,
                  boolean forceUpdate);

    void loadNewsDetail(Context ctx,
                        String url,
                        String docId,
                        List<RequestParameter> params,
                        NewsDetailLoadListener listener,
                        boolean forceUpdate);
}
