package com.gypsophila.trygank.business.gank.model;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public interface GankDateLoadListener {

    void onSuccess(GankIOPlusBean bean);

    void onFailed(String errorMessage);
}
