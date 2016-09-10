package com.gypsophila.trygank.news.presenter;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.engine.AppConstants;
import com.gypsophila.trygank.news.model.INewsBusiness;
import com.gypsophila.trygank.news.model.NewsBean;
import com.gypsophila.trygank.news.model.NewsBusinessImpl;
import com.gypsophila.trygank.news.model.NewsLoadListener;
import com.gypsophila.trygank.news.view.INewsView;
import com.gypsophila.trygank.news.view.NewsFragment;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsPresenterImpl implements INewsPresenter, NewsLoadListener {

    private INewsView mNewsView;
    private INewsBusiness mNewsBusiness;

    public NewsPresenterImpl(INewsView newsView) {
        mNewsView = newsView;
        mNewsBusiness = new NewsBusinessImpl();
    }

    @Override
    public void loadNews(BaseActivity activity, final int type, int pageIndex, List<RequestParameter> params, boolean forceUpdate) {
        if (pageIndex == 0) {
            mNewsView.showProgress();
        }
        mNewsBusiness.loadNews(
                activity,
                getUrl(type, pageIndex),
                type,
                params,
                this,
                true);
    }

    public String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(AppConstants.TOP_URL).append(AppConstants.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(AppConstants.COMMON_URL).append(AppConstants.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(AppConstants.COMMON_URL).append(AppConstants.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(AppConstants.COMMON_URL).append(AppConstants.JOKE_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(AppConstants.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> newsBeanList) {
        mNewsView.hideProgress();
        mNewsView.addNews(newsBeanList);
    }

    @Override
    public void onFail(String errorMessage) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg();
    }
}
