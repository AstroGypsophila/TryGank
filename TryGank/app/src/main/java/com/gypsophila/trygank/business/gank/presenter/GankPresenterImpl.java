package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.AppConstants;
import com.gypsophila.trygank.business.gank.FilterType;
import com.gypsophila.trygank.business.gank.model.GankBusinessImpl;
import com.gypsophila.trygank.business.gank.model.GankLoadListener;
import com.gypsophila.trygank.business.gank.model.IGankBusiness;
import com.gypsophila.trygank.business.gank.view.IGankView;
import com.gypsophila.trygank.entity.GankBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/5
 */
public class GankPresenterImpl implements IGankPresenter, GankLoadListener<GankBean> {

    private IGankView mGankView;
    private IGankBusiness mGankBusiness;
    private boolean isFirstLoad = true;
    private FilterType mFilter = FilterType.ALL;


    public GankPresenterImpl(IGankView gankView) {
        mGankView = gankView;
        mGankBusiness = new GankBusinessImpl();
    }

    @Override
    public void loadGank(Context ctx, String type, int pageIndex, List<RequestParameter> params) {

        if (pageIndex == 1) {
            mGankView.showProgress();
        }
        mGankBusiness.loadBeans((BaseActivity) ctx, getUrl(type, pageIndex), params, this);
    }

    @Override
    public void loadGankFromDataBase(Context ctx) {
        mGankView.showProgress();
        mGankBusiness.loadBeansFromDataBase((BaseActivity) ctx, FilterType.getFilter(mFilter), this);

    }

    @Override
    public void setFilter(FilterType requestType) {
        mFilter = requestType;
    }

    public String getUrl(String type, int pageIndex) {
        StringBuffer sb = new StringBuffer(AppConstants.GANK_API).append("data/");
        sb.append(type);
        sb.append("/").append(AppConstants.PAGE_SIZE).append("/").append(pageIndex);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<GankBean> gankBeanList) {
        mGankView.hideProgress();
        mGankView.addNews(gankBeanList);
    }

    @Override
    public void onFailed(String errorMessage) {
        mGankView.hideProgress();
        mGankView.showLoadFailMsg(errorMessage);
    }
}
