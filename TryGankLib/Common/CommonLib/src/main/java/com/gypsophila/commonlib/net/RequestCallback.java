package com.gypsophila.commonlib.net;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public interface RequestCallback {

    void onSuccess(String content);

    void onFail(String errorMessage);
}
