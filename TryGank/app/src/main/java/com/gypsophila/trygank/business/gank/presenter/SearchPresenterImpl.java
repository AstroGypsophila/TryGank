package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.model.GankLoadListener;
import com.gypsophila.trygank.business.gank.model.IGankBusiness;
import com.gypsophila.trygank.entity.SearchBean;
import com.gypsophila.trygank.business.gank.model.SearchBusinessImpl;
import com.gypsophila.trygank.business.gank.view.ISearchView;
import com.gypsophila.trygank.business.AppConstants;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public class SearchPresenterImpl implements IGankPresenter, GankLoadListener<SearchBean> {

    private ISearchView mSearchView;
    private IGankBusiness mSearchBusiness;

    public SearchPresenterImpl(ISearchView searchView) {
        mSearchView = searchView;
        mSearchBusiness = new SearchBusinessImpl();
    }

    @Override
    public void loadGank(Context ctx, String type, int pageIndex, List<RequestParameter> params) {
        mSearchView.showProgress();
        mSearchBusiness.loadBeans((BaseActivity)ctx, getUrl(type, pageIndex), params, this);

    }

    @Override
    public void loadGankFromDataBase(Context ctx) {

    }

    @Override
    public void onSuccess(List<SearchBean> beanList) {
        mSearchView.hideProgress();
        mSearchView.addNews(beanList);
    }

    @Override
    public void onFailed(String errorMessage) {
        mSearchView.hideProgress();
    }

    private String getUrl(String keyWord, int pageIndex) {
        String type = "all";
        StringBuffer sb = new StringBuffer(AppConstants.GANK_API).append("search/query/");
        sb.append(keyWord);
        sb.append("/category/").append(type).append("/count/")
                .append(AppConstants.PAGE_SIZE)
                .append("/page/").append(pageIndex);
        return sb.toString();
    }
}
