package com.gypsophila.trygank.business.gank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gypsophila.trygank.entity.GankDatePlusBean;
import com.gypsophila.trygank.entity.GankIOPlusBean;
import com.gypsophila.trygank.entity.GankPlusBean;
import com.gypsophila.trygank.entity.SearchPlusBean;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public class GankJsonUtils {

    public static SearchPlusBean readJsonSearchPlusBean(String strJson) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        SearchPlusBean bean = gson.fromJson(strJson, SearchPlusBean.class);
        return bean;
    }

    public static GankPlusBean readJsonGankPlusBean(String strJson) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        GankPlusBean bean = gson.fromJson(strJson, GankPlusBean.class);
        return bean;
    }

    public static GankIOPlusBean readJsonGankIOPlusBean(String strJson) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        GankIOPlusBean bean = gson.fromJson(strJson, GankIOPlusBean.class);
        return bean;
    }

    public static GankDatePlusBean readJsonGankDatePlusBean(String strJson) {
        Gson gson = new Gson();
        GankDatePlusBean bean = gson.fromJson(strJson, GankDatePlusBean.class);
        return bean;
    }

}
