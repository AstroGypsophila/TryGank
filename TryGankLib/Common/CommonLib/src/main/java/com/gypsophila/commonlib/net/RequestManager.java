package com.gypsophila.commonlib.net;

import com.gypsophila.commonlib.activity.BaseActivity;

import java.util.ArrayList;
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
            for (final HttpRequest request : requestList) {
                if (request.getRequest() != null) {
                    try {
                        request.getRequest().abort();
                        requestList.remove(request);
//                    requestList.remove(request.getRequest());
                    } catch (final UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public HttpRequest createRequest(final URLData data, final RequestCallback callback) {
        return createRequest(data, null, callback);
    }

    /**
     * 构造Request并加入列表
     */
    public HttpRequest createRequest(final URLData data, final List<RequestParameter> parameters,
                                     final RequestCallback callback) {
        final HttpRequest request = new HttpRequest(data, parameters, callback);
        addRequest(request);
        return request;
    }


}
