package com.gypsophila.trygank.business.news.view;

import com.gypsophila.trygank.business.news.model.NewsBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public interface INewsView {

    //加载过程显示进度反馈
    void showProgress();

    void addNews(List<NewsBean> newsBeanList);

    void hideProgress();

    void showLoadFailMsg();
}
