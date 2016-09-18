package com.gypsophila.trygank.news.model;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/18
 */
public interface NewsDetailLoadListener {

    void onSuccess(NewsDetailBean newsDetailBean);

    void onFail(String errorMessage);
}
