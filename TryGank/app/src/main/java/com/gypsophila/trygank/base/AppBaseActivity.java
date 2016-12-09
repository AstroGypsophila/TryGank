package com.gypsophila.trygank.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.net.RequestCallback;
import com.gypsophila.trygank.umeng.UmengEvent;
import com.gypsophila.trygank.utils.ThemeUtil;

/**
 * Created by Gypsophila on 2016/8/1.
 */
public abstract class AppBaseActivity extends BaseActivity {

    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtil.changTheme(this, ThemeUtil.getCurrentTheme(this));
        super.onCreate(savedInstanceState);
    }

    public abstract class AbstractRequestCallback implements RequestCallback {

        public abstract void onSuccess(String content);

        @Override
        public void onFail(String errorMessage) {
            dialog.dismiss();
            new AlertDialog.Builder(AppBaseActivity.this)
                    .setTitle("出错啦").setMessage(errorMessage)
                    .setPositiveButton("确定", null).show();
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengEvent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengEvent.onPause(this);
    }
}
