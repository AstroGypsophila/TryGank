package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.trygank.entity.GankDatePlusBean;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/11/10
 */
public interface GankDateLoadListener {

    void onSuccess(GankDatePlusBean bean);

    void onFailed(String errorMessage);
}
