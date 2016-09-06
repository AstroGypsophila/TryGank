package com.gypsophila.commonlib.net;

import com.gypsophila.commonlib.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gypsophila on 2016/8/1.
 */
public class RequestManager {

    private ArrayList<HttpRequest> requestList = null;

    public RequestManager(final BaseActivity activity) {
        //异步请求列表
        requestList = new ArrayList<>();
    }

    /**
     * 添加Request到列表
     */
    public void addRequest(final HttpRequest request) {
        requestList.add(request);
    }

    /**
     * 取消网络请求
     */
    public void cancelRequest() {
        if (requestList != null && requestList.size() > 0) {
            //解决java.util.ConcurrentModificationException
            Iterator<HttpRequest> iterator = requestList.iterator();
            while (iterator.hasNext()) {
                HttpRequest request = iterator.next();
                if (request != null) {
                    try {
                        request.getRequest().abort();
                        iterator.remove();
//                    requestList.remove(request.getRequest());
                    } catch (final UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public HttpRequest createRequest(final String url, final RequestCallback callback) {
        return createRequest(url, null, callback);
    }

    /**
     * 构造Request并加入列表
     */
    public HttpRequest createRequest(final String url, final List<RequestParameter> parameters,
                                     final RequestCallback callback) {
        final HttpRequest request = new HttpRequest(url, parameters, callback);
        addRequest(request);
        return request;
    }


}
