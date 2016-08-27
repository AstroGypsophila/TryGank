package com.gypsophila.trygank.news.presenter;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.news.JsonUtils;
import com.gypsophila.trygank.news.model.INewsBusiness;
import com.gypsophila.trygank.news.model.NewsBean;
import com.gypsophila.trygank.news.model.NewsBusinessImpl;
import com.gypsophila.trygank.news.view.INewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsPresenterImpl implements INewsPresenter {

    private INewsView mNewsView;
    private INewsBusiness mNewsBusiness;

    public NewsPresenterImpl(INewsView newsView) {
        mNewsView = newsView;
        mNewsBusiness = new NewsBusinessImpl();
    }

    @Override
    public void loadNews(BaseActivity activity, String apiKey, List<RequestParameter> params, RequestCallback callBack, boolean forceUpdate) {
        mNewsView.showProgress();
        mNewsBusiness.loadNews(
                activity,
                "top",
                params,
                new RequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        mNewsView.hideProgress();
                        mNewsView.addNews(readJsonNewsBeans(content));
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        mNewsView.hideProgress();
                        mNewsView.showLoadFailMsg();
                    }
                }, true);
    }


    /**
     * 将获取到的json转换为新闻列表对象
     * @return
     */
    public static List<NewsBean> readJsonNewsBeans(String res) {
        List<NewsBean> beans = new ArrayList<NewsBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get("T1348647909107");
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    NewsBean news = JsonUtils.deserialize(jo, NewsBean.class);
                    beans.add(news);
                }
            }
        } catch (Exception e) {
//            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
        return beans;
    }
}
