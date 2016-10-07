package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public interface IGankBusiness {

    void loadBeans(BaseActivity activity,
                   String url,
                   List<RequestParameter> parameters,
                   GankLoadListener listener);

    void loadBeansFromDataBase(BaseActivity activity, GankLoadListener listener);


}
