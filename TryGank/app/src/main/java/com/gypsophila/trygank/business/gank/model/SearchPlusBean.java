package com.gypsophila.trygank.business.gank.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public class SearchPlusBean implements Serializable {

    public int count;
    public boolean error;

    public List<SearchBean> results;
}
