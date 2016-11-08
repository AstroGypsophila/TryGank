package com.gypsophila.trygank.business.gank.view;

import com.gypsophila.trygank.business.gank.model.GankIOPlusBean;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public interface ITodayView {
    //加载过程显示进度反馈
    void showProgress();

    void addData(GankIOPlusBean bean);

    void hideProgress();

    void showLoadFailMsg();
}
