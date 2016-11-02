package com.gypsophila.trygank.business.gank.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public class GankBean implements Serializable {

    @SerializedName("_id")
    public String id;
    @SerializedName("createdAt")
    public String createTime;
    public String desc;
    public String[] images;
    @SerializedName("publishedAt")
    public String publishTime;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
}
