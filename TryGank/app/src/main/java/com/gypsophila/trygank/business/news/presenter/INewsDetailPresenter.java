package com.gypsophila.trygank.business.news.presenter;

import android.content.Context;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/18
 */
public interface INewsDetailPresenter {


    void loadNewsDetail(Context ctx,
                        String docId,
                        List<RequestParameter> params,
                        boolean forceUpdate);
}
