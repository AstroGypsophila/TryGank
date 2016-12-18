package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.FilterType;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public interface IGankPresenter {

    void loadGank(Context ctx, String type, int pageIndex, List<RequestParameter> params);

    void loadGankFromDataBase(Context ctx);

    void setFilter(FilterType requestType);
}
