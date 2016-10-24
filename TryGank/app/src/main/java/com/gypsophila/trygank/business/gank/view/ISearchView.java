package com.gypsophila.trygank.business.gank.view;

import com.gypsophila.trygank.business.gank.model.SearchBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public interface ISearchView {

    //加载过程显示进度反馈
    void showProgress();

    void addNews(List<SearchBean> searchBeanList);

    void hideProgress();

    void showLoadFailMsg();
}
