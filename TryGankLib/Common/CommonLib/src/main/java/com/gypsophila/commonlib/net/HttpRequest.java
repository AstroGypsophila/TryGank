package com.gypsophila.commonlib.net;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.gypsophila.commonlib.cache.CacheManager;
import com.gypsophila.commonlib.utils.BaseUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public class HttpRequest implements Runnable {

    //区别get和post的枚举
    public static final String REQUEST_GET = "get";
    public static final String REQUEST_POST = "post";

    private URLData urlData;
    private String url; //原始url
    private String newUrl; //拼接key-value后的url
    private HttpUriRequest request = null;
    private RequestCallback requestCallback = null;
    private List<RequestParameter> parameters = null;
    private HttpResponse response = null;
    private DefaultHttpClient httpClient;

    //切换会ui线程
    protected Handler handler;

    public HttpRequest(final URLData data, final List<RequestParameter> params, final RequestCallback callback) {
        urlData = data;
        url = urlData.getUrl();
        parameters = params;
        requestCallback = callback;
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        handler = new Handler();
    }

    @Override

    public void run() {

        try {
            if (urlData.getNetType().equals(REQUEST_GET)) {
                //添加参数
                StringBuffer paramBuffer = new StringBuffer();
                if (parameters != null && parameters.size() > 0) {

                    //key进行排序
                    sortKeys();
                    for (final RequestParameter p : parameters) {
                        if (paramBuffer.length() == 0) {
                            paramBuffer.append(p.getName() + "=" + BaseUtils.UrlEncodeUnicode(p.getValue()));
                        } else {
                            paramBuffer.append("&" + p.getName() + "=" + BaseUtils.UrlEncodeUnicode(p.getValue()));
                        }
                    }
                    newUrl = url + "?" + paramBuffer.toString();
                } else {
                    newUrl = url;
                }

                //如果这个get的api有缓存时间(大于0)
                if (urlData.getExpires() > 0) {
                    final String content = CacheManager.getInstance().getFileCache(newUrl);
                    if (content != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCallback.onSuccess(content);
                            }
                        });
                    }
                    return;
                }
                request = new HttpGet(newUrl);
            } else if (urlData.getNetType().equals(REQUEST_POST)) {
                request = new HttpPost(url);
                //添加参数
                final List<BasicNameValuePair> list = new ArrayList<>();
                if (parameters != null && parameters.size() > 0) {
                    for (final RequestParameter p : parameters) {
                        list.add(new BasicNameValuePair(p.getName(), p.getValue()));
                    }
                    ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
                }
            } else {
                return;
            }

            request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

            //发送请求
            response = httpClient.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                final ByteArrayOutputStream content = new ByteArrayOutputStream();
                response.getEntity().writeTo(content);
                String strResponse = new String(content.toByteArray()).trim();
//                strResponse = "{'isError':false,'errorType':0,'errorMessage':'','result':{'city':'北京','cityid':'101010100','temp':'17','WD':'西南风','WS':'2级','SD':'54%','WSE':'2','time':'23:15','isRadar':'1','Radar':'JC_RADAR_AZ9010_JB','njd':'暂无实况','qy':'1016'}}";

                //设置回调函数
                if (requestCallback != null) {
                    final Response responseInJson = JSON.parseObject(strResponse, Response.class);
                    if (responseInJson.hasError()) {
                        handleNetworkError(responseInJson.getErrorMessage());
                    } else {
                        //把成功获取的数据记录到缓存
                        if (urlData.getNetType().equals(REQUEST_GET) &&
                                urlData.getExpires() > 0) {
                            CacheManager.getInstance().putFileCache(newUrl, responseInJson.getResult(), urlData.getExpires());
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCallback.onSuccess(responseInJson.getResult());
                            }
                        });
                    }
                }
            }
        } catch (final java.lang.IllegalArgumentException e) {
            handleNetworkError("网络异常");
        } catch (final UnsupportedEncodingException e) {
            handleNetworkError("网络异常");
        } catch (final IOException e) {
            handleNetworkError("网络异常");
        }
    }

    void sortKeys() {
        for (int i = 1; i < parameters.size(); i++) {
            for (int j = i; j > 0; j--) {
                RequestParameter p1 = parameters.get(j - 1);
                RequestParameter p2 = parameters.get(j);
                if (compare(p1.getName(), p2.getName())) {
                    // 交互p1和p2这两个对象，写的超级恶心
                    String name = p1.getName();
                    String value = p1.getValue();

                    p1.setName(p2.getName());
                    p1.setValue(p2.getValue());

                    p2.setName(name);
                    p2.setValue(value);
                }
            }
        }
    }

    // 返回true说明str1大，返回false说明str2大
    boolean compare(String str1, String str2) {
        String uppStr1 = str1.toUpperCase();
        String uppStr2 = str2.toUpperCase();

        boolean str1IsLonger = true;
        int minLen = 0;

        if (str1.length() < str2.length()) {
            minLen = str1.length();
            str1IsLonger = false;
        } else {
            minLen = str2.length();
            str1IsLonger = true;
        }

        for (int index = 0; index < minLen; index++) {
            char ch1 = uppStr1.charAt(index);
            char ch2 = uppStr2.charAt(index);
            if (ch1 != ch2) {
                if (ch1 > ch2) {
                    return true; // str1大
                } else {
                    return false; // str2大
                }
            }
        }

        return str1IsLonger;
    }

    private void handleNetworkError(final String errorMessage) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                requestCallback.onFail(errorMessage);
            }
        });
    }

    public static String inputStreamToString(final InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * 获取HttpUriRequest请求
     *
     * @return
     */
    public HttpUriRequest getRequest() {
        return request;
    }
}
