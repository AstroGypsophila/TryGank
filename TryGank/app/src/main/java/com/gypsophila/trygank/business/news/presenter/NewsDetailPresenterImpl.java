package com.gypsophila.trygank.business.news.presenter;

import android.content.Context;

import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.news.model.NewsBusinessImpl;
import com.gypsophila.trygank.business.news.model.NewsDetailLoadListener;
import com.gypsophila.trygank.business.AppConstants;
import com.gypsophila.trygank.business.news.model.INewsBusiness;
import com.gypsophila.trygank.business.news.model.NewsDetailBean;
import com.gypsophila.trygank.business.news.view.INewsDetaiView;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/9/18
 */
public class NewsDetailPresenterImpl implements INewsDetailPresenter, NewsDetailLoadListener {

    private INewsBusiness mNewsBusiness;
    private INewsDetaiView mNewsDetailView;

    public NewsDetailPresenterImpl(INewsDetaiView newsDetaiView) {
        mNewsDetailView = newsDetaiView;
        mNewsBusiness = new NewsBusinessImpl();
    }

    @Override
    public void loadNewsDetail(Context ctx, String docId, List<RequestParameter> params, boolean forceUpdate) {
        mNewsDetailView.showProgress();
        mNewsBusiness.loadNewsDetail(ctx, getUrl(docId), docId, params, this, forceUpdate);
    }

    private String getUrl(String docId) {
        return AppConstants.NEWS_DETAIL_URL + docId + AppConstants.NEWS_DETAIL_END_URL;
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        mNewsDetailView.hideProgress();
        mNewsDetailView.showNewsDetail(newsDetailBean.getBody());
    }

    @Override
    public void onFail(String errorMessage) {
        mNewsDetailView.hideProgress();
    }
}
