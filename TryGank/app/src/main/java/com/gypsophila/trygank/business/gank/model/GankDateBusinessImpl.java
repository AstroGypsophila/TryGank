package com.gypsophila.trygank.business.gank.model;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;
import com.gypsophila.trygank.business.gank.GankJsonUtils;
import com.gypsophila.trygank.business.RemoteService;
import com.gypsophila.trygank.entity.GankDatePlusBean;
import com.gypsophila.trygank.entity.GankIOPlusBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/11/6
 */
public class GankDateBusinessImpl implements IGankDateBusiness {

    @Override
    public void loadGankDateBean(BaseActivity activity,
                                 String url,
                                 List<RequestParameter> parameters,
                                 final GankTodayLoadListener listener) {
        RequestCallback callback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                GankIOPlusBean bean = GankJsonUtils.readJsonGankIOPlusBean(content);
//                GankIOBean gankIOBean = bean.results.get(0);
//                parseHtmlContent(gankIOBean);
                listener.onSuccess(bean);
            }

            @Override
            public void onFail(String errorMessage) {

            }
        };
        RemoteService.getInstance().invoke(activity, url, parameters, callback);

    }

    @Override
    public void loadDateData(BaseActivity activity, String url, List<RequestParameter> parameters, final GankDateLoadListener listener) {
        RequestCallback callback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                GankDatePlusBean bean = GankJsonUtils.readJsonGankDatePlusBean(content);
                listener.onSuccess(bean);
            }

            @Override
            public void onFail(String errorMessage) {

            }
        };
        RemoteService.getInstance().invoke(activity, url, parameters, callback);
    }

//    private void parseHtmlContent(GankIOBean bean) {
//        Document document = Jsoup.parse(bean.content);
//        //取得福利图
//        Element welfare = document.select("p img").first();
//        bean.welfare = welfare.attr("src");
//
//        Elements catagories = document.select("h3");
//
//        for (Element catagory : catagories) {
//            bean.catagories.add(catagories.text());
//            Element ulElement = catagory.nextElementSibling();
//            Elements children = ulElement.children();
//            for (Element element : children) {
//                DailyItemBean itemBean = new DailyItemBean();
//                itemBean.text = element.text();
//                itemBean.linkUrl = element.attr("href");
//                itemBean.type = catagory.text();
//            }
//        }
//    }
}
