package com.gypsophila.trygank.business.gank.view;

import com.gypsophila.trygank.entity.GankIOPlusBean;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public interface ITodayView {
    //加载过程显示进度反馈
    void showProgress();

    void addData(GankIOPlusBean bean);

    void hideProgress();

    void showLoadFailMsg();
}
