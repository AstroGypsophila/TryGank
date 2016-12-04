package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.trygank.entity.GankIOPlusBean;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public interface GankTodayLoadListener {

    void onSuccess(GankIOPlusBean bean);

    void onFailed(String errorMessage);
}
