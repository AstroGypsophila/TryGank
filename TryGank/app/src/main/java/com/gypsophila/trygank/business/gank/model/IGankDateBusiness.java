package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public interface IGankDateBusiness {

    void loadGankDateBean(BaseActivity activity, String url, List<RequestParameter> parameters, GankDateLoadListener listener);

}
