package com.gypsophila.commonlib.net;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public interface RequestCallback {

    public void onSuccess(String content);

    public void onFail(String errorMessage);
}
