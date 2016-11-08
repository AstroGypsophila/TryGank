package com.gypsophila.trygank.business.gank.presenter;

import android.content.Context;

import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public interface IGankDatePresenter {

    void loadGank(Context ctx, List<RequestParameter> params);
}
