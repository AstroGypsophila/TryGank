package com.gypsophila.trygank.business.news.model;

import java.util.List;

/**
 * Description : 新闻加载接口，解决在业务层前解析数据返回
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/28
 */
public interface NewsLoadListener {

    void onSuccess(List<NewsBean> newsBeanList);

    void onFail(String errorMessage);
}
