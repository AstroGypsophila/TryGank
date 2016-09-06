package com.gypsophila.trygank.engine;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.DefaultThreadPool;
import com.gypsophila.commonlib.net.HttpRequest;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.commonlib.net.RequestParameter;

import java.util.List;


public class RemoteService {
    private static RemoteService service = null;

    private RemoteService() {

    }

    public static synchronized RemoteService getInstance() {
        if (RemoteService.service == null) {
            RemoteService.service = new RemoteService();
        }
        return RemoteService.service;
    }

    public void invoke(final BaseActivity activity,
                       final String url,
                       final List<RequestParameter> params,
                       final RequestCallback callBack) {

        invoke(activity, url, params, callBack, false);
    }

    public void invoke(final BaseActivity activity,
                       final String url,
                       final List<RequestParameter> params,
                       final RequestCallback callBack,
                       final boolean forceUpdate) {

//        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
//        if (null != urlData.getMockClass()) {
//            try {
//                MockService mockService = (MockService) Class.forName(
//                        urlData.getMockClass()).newInstance();
//                String strResponse = mockService.getJsonData();
//
//                final Response responseInJson = JSON.parseObject(strResponse,
//                        Response.class);
//                if (callBack != null) {
//                    if (responseInJson.hasError()) {
//                        callBack.onFail(responseInJson.getErrorMessage());
//                    } else {
//                        callBack.onSuccess(responseInJson.getResult());
//                    }
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        } else {
//            if (forceUpdate) {
//                //如果强制更新，那么就把过期时间强制设置为0
//                urlData.setExpires(0);
//            }
        HttpRequest request = activity.getRequestManager().createRequest(
                url, params, callBack);
        DefaultThreadPool.getInstance().execute(request);
    }

//    }
}