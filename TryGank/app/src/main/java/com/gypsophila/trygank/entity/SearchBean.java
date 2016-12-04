package com.gypsophila.trygank.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public class SearchBean implements Serializable {

    /**
     * 文章标题
     */
    public String desc;
    @SerializedName("ganhuo_id")
    public String id;
    @SerializedName("publishedAt")
    public Date publishTime;
    public String readability;
    public String type;
    public String url;
    public String who;
}
