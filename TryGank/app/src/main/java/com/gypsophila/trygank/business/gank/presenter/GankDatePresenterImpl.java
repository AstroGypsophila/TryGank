package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.model.GankDateBusinessImpl;
import com.gypsophila.trygank.business.gank.model.GankDateLoadListener;
import com.gypsophila.trygank.business.gank.model.GankIOPlusBean;
import com.gypsophila.trygank.business.gank.model.IGankDateBusiness;
import com.gypsophila.trygank.business.gank.view.ITodayView;
import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public class GankDatePresenterImpl implements IGankDatePresenter ,GankDateLoadListener{

    private IGankDateBusiness mGankDateBusiness;
    private ITodayView mTodayView;

    public GankDatePresenterImpl(ITodayView todayView) {
        mGankDateBusiness = new GankDateBusinessImpl();
        mTodayView = todayView;
    }

    @Override
    public void loadGank(Context ctx, List<RequestParameter> params) {
        mTodayView.showProgress();
        mGankDateBusiness.loadGankDateBean((BaseActivity) ctx, getUrl(), params, this);
    }

    private String getUrl() {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Logger.t("cj_data").w(year + " " + month + " " + day);
        return "http://gank.io/api/day/2016/11/8";
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
