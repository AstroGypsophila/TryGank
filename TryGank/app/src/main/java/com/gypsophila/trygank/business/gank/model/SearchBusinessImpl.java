package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.GankJsonUtils;
import com.gypsophila.trygank.business.RemoteService;
import com.gypsophila.trygank.entity.SearchPlusBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public class SearchBusinessImpl implements IGankBusiness {

    @Override
    public void loadBeans(BaseActivity activity,
                          String url,
                          List<RequestParameter> parameters,
                          final GankLoadListener listener) {
        RequestCallback callback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                SearchPlusBean searchPlusBean = GankJsonUtils.readJsonSearchPlusBean(content);
                listener.onSuccess(searchPlusBean.results);
            }

            @Override
            public void onFail(String errorMessage) {

            }
        };
        RemoteService.getInstance().invoke(activity, url, parameters, callback);
    }

    @Override
    public void loadBeansFromDataBase(BaseActivity activity, String filter, GankLoadListener listener) {

    }
}
