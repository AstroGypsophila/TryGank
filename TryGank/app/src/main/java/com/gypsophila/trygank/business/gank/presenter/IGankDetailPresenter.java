package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.trygank.business.gank.model.GankBean;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public interface IGankDetailPresenter {

    void loadGank(Context ctx, String gankId);

    int delteGank(Context ctx, String gankId);

    int addGank(Context ctx, GankBean gankBean);
}
