package com.gypsophila.trygank.business.gank.view;

import com.gypsophila.trygank.entity.GankBean;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public interface IGankDetail {

    void initFavorite(GankBean gankBean);

    void share(String url);
}
