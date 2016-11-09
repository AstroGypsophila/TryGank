package com.gypsophila.trygank.business.gank.model;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/10
 */
public interface GankDateLoadListener {

    void onSuccess(GankDatePlusBean bean);

    void onFailed(String errorMessage);
}
