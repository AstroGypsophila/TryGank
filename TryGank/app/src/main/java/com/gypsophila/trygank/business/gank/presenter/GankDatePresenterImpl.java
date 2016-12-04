package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.model.GankDateBusinessImpl;
import com.gypsophila.trygank.business.gank.model.GankDateLoadListener;
import com.gypsophila.trygank.entity.GankDatePlusBean;
import com.gypsophila.trygank.entity.GankIOPlusBean;
import com.gypsophila.trygank.business.gank.model.GankTodayLoadListener;
import com.gypsophila.trygank.business.gank.model.IGankDateBusiness;
import com.gypsophila.trygank.business.gank.view.ITodayView;
import com.gypsophila.trygank.business.AppConstants;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public class GankDatePresenterImpl implements IGankDatePresenter, GankTodayLoadListener {

    private IGankDateBusiness mGankDateBusiness;
    private ITodayView mTodayView;

    public GankDatePresenterImpl(ITodayView todayView) {
        mGankDateBusiness = new GankDateBusinessImpl();
        mTodayView = todayView;
    }

    @Override
    public void loadGank(final Context ctx, List<RequestParameter> params) {
        mTodayView.showProgress();
        mGankDateBusiness.loadDateData((BaseActivity) ctx, AppConstants.GANK_HISTORY, params, new GankDateLoadListener() {
            @Override
            public void onSuccess(GankDatePlusBean bean) {
                String latest = bean.results.get(0);
                String[] date = latest.split("-");
                mGankDateBusiness.loadGankDateBean((BaseActivity) ctx, getUrl(date[0], date[1], date[2]),
                        null, GankDatePresenterImpl.this);
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

    private String getUrl(String year, String month, String day) {
        return AppConstants.GANK_DAY + year + "/" + month + "/" + day;
    }

    @Override
    public void onSuccess(GankIOPlusBean bean) {
        mTodayView.hideProgress();
        mTodayView.addData(bean);

    }

    @Override
    public void onFailed(String errorMessage) {
        mTodayView.hideProgress();
    }
}
