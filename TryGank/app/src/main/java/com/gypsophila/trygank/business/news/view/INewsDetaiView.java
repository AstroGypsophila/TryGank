package com.gypsophila.trygank.business.news.view;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/17
 */
public interface INewsDetaiView {

    void showProgress();

    void hideProgress();

    void showNewsDetail(String content);
}
