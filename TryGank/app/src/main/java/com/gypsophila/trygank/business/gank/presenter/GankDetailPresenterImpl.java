package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.trygank.entity.GankBean;
import com.gypsophila.trygank.business.gank.view.IGankDetail;
import com.gypsophila.trygank.db.GankDataBaseManager;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/7
 */
public class GankDetailPresenterImpl implements IGankDetailPresenter {


    private IGankDetail mGankDetail;

    public GankDetailPresenterImpl(IGankDetail gankDetail) {
        mGankDetail = gankDetail;
    }

    @Override
    public void loadGank(Context ctx, String gankId) {
        GankBean gankBean = GankDataBaseManager.getGankBean(ctx, gankId);
        mGankDetail.initFavorite(gankBean);
    }

    @Override
    public int delteGank(Context ctx, String gankId) {
        int result = GankDataBaseManager.deleteGankBean(ctx, gankId);
        return result;
    }

    @Override
    public int addGank(Context ctx, GankBean gankBean) {
        int result = GankDataBaseManager.updateOrInsertGank(ctx, gankBean);
        return result;
    }
}
