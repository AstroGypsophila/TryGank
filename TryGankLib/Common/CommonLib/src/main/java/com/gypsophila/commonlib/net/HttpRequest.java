package com.gypsophila.commonlib.net;

import android.os.Handler;

import com.gypsophila.commonlib.utils.BaseUtils;
import com.orhanobut.logger.Logger;

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

    private String mUrl; //原始url
    private String mNewUrl; //拼接key-value后的url
    private HttpUriRequest mRequest = null;
    private RequestCallback mRequestCallback = null;
    private List<RequestParameter> mParameters = null;
    private HttpResponse mResponse = null;
    private DefaultHttpClient mHttpClient;

    /**
     * 请求方式
     */
    private String mRequestType = REQUEST_GET;

    //切换回ui线程
    protected Handler handler;

    public HttpRequest(final String url, final List<RequestParameter> params, final RequestCallback callback) {
        mUrl = url;
        mParameters = params;
        mRequestCallback = callback;
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
        }
        handler = new Handler();
    }

    public void setRequestType(String requestType) {
        mRequestType = requestType;
    }

    @Override

    public void run() {

        try {
            if (REQUEST_GET.equals(mRequestType)) {
                //添加参数
                StringBuffer paramBuffer = new StringBuffer();
                if (mParameters != null && mParameters.size() > 0) {

                    //key进行排序
                    sortKeys();
                    for (final RequestParameter p : mParameters) {
                        if (paramBuffer.length() == 0) {
                            paramBuffer.append(p.getName() + "=" + BaseUtils.UrlEncodeUnicode(p.getValue()));
                        } else {
                            paramBuffer.append("&" + p.getName() + "=" + BaseUtils.UrlEncodeUnicode(p.getValue()));
                        }
                    }
                    mNewUrl = mUrl + "?" + paramBuffer.toString();
                } else {
                    mNewUrl = mUrl;
                }

                //如果这个get的api有缓存时间(大于0)
//                if (urlData.getExpires() > 0) {
//                    final String content = CacheManager.getInstance().getFileCache(mNewUrl);
//                    if (content != null) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRequestCallback.onSuccess(content);
//                            }
//                        });
//                    }
//                    return;
//                }
                mRequest = new HttpGet(mNewUrl);
            } else if (REQUEST_POST.equals(mRequestType)) {
                mRequest = new HttpPost(mUrl);
                //添加参数
                final List<BasicNameValuePair> list = new ArrayList<>();
                if (mParameters != null && mParameters.size() > 0) {
                    for (final RequestParameter p : mParameters) {
                        list.add(new BasicNameValuePair(p.getName(), p.getValue()));
                    }
                    ((HttpPost) mRequest).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
                }
            } else {
                return;
            }

            mRequest.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            mRequest.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

            //发送请求
            mResponse = mHttpClient.execute(mRequest);
            final int statusCode = mResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                final ByteArrayOutputStream content = new ByteArrayOutputStream();
                mResponse.getEntity().writeTo(content);
                final String strResponse = new String(content.toByteArray()).trim();
                Logger.t("AstroGypsophila").w(strResponse);

//                strResponse = "{'isError':false,'errorType':0,'errorMessage':'','result':{'city':'北京','cityid':'101010100','temp':'17','WD':'西南风','WS':'2级','SD':'54%','WSE':'2','time':'23:15','isRadar':'1','Radar':'JC_RADAR_AZ9010_JB','njd':'暂无实况','qy':'1016'}}";

                //设置回调函数
                if (mRequestCallback != null) {
//                    final Response responseInJson = JSON.parseObject(strResponse, Response.class);
//                    if (responseInJson.hasError()) {
//                        handleNetworkError(responseInJson.getErrorMessage());
//                    } else {
//                        //把成功获取的数据记录到缓存
//                        if (urlData.getNetType().equals(REQUEST_GET) &&
//                                urlData.getExpires() > 0) {
//                            CacheManager.getInstance().putFileCache(newUrl, responseInJson.getResult(), urlData.getExpires());
//                        }
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                requestCallback.onSuccess(responseInJson.getResult());
//                            }
//                        });
//                    }
                    //由于获取api接口json返回格式不一，所以解析只能放在实际项目中，先只放入原json字符串
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mRequestCallback.onSuccess(strResponse);
                        }
                    });
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
        for (int i = 1; i < mParameters.size(); i++) {
            for (int j = i; j > 0; j--) {
                RequestParameter p1 = mParameters.get(j - 1);
                RequestParameter p2 = mParameters.get(j);
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
                mRequestCallback.onFail(errorMessage);
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
        return mRequest;
    }
}
