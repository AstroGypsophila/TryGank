package com.gypsophila.commonlib.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gypsophila.commonlib.net.RequestManager;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected RequestManager requestManager = null;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        requestManager = new RequestManager();
        super.onCreate(savedInstanceState);
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    protected abstract void initVariables();

    protected abstract void loadData();

    protected abstract void initViews(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        /**
         * 在activity销毁的时候同时设置停止请求，停止线程请求回调
         */
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onDestroy();
    }

//    @Override
//    protected void onPause() {
//        /**
//         * 在activity停止的时候同时设置停止请求，停止线程请求回调
//         */
//        if (requestManager != null) {
//            requestManager.cancelRequest();
//        }
//        super.onPause();
//    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
