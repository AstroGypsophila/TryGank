package com.gypsophila.trygank.business.gank.view;

import com.gypsophila.trygank.entity.GankBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public interface IGankView {

    //加载过程显示进度反馈
    void showProgress();

    void addNews(List<GankBean> gankBeanList);

    void hideProgress();

    void showLoadFailMsg();
}
