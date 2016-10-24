package com.gypsophila.trygank.business.gank.model;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public interface GankLoadListener<T> {

    void onSuccess(List<T> beanList);

    void onFailed(String errorMessage);
}
