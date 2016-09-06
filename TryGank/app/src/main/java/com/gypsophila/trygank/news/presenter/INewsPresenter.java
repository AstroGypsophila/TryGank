package com.gypsophila.trygank.news.presenter;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public interface INewsPresenter {

    void loadNews(BaseActivity activity,
                  int type,
                  int pageIndex,
                  List<RequestParameter> params,
                  boolean forceUpdate);
}
