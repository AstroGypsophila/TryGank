package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.business.gank.presenter.GankDetailPresenterImpl;
import com.gypsophila.trygank.business.gank.presenter.IGankDetailPresenter;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/6
 */
public class GankDetailActivity extends SwipeBackActivity implements IGankDetail {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebView mWebView;
    private Toolbar mToolbar;
    private IGankDetailPresenter mGankDetailPresenter;
    private Context mContext;
    private GankBean mGankBean;
    private boolean mCurrentStatus = false;
    private boolean mInitStatus = false;

    public static void openWebView(Context ctx, GankBean gankBean) {
        Intent intent = new Intent(ctx, GankDetailActivity.class);
        intent.putExtra("gankBean", gankBean);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_gankdetail);
        mGankBean = (GankBean) getIntent().getSerializableExtra("gankBean");
        mGankDetailPresenter = new GankDetailPresenterImpl(this);
        mGankDetailPresenter.loadGank(mContext, mGankBean.id);
        initViews();
        initData();
    }

    private void initData() {
        //判断是否favorite

    }

    private void initViews() {
        initToolbar();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(mGankBean.url);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.gankdetail_toolbar_menu);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

    }

    Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_favorite) {
                mCurrentStatus = !mCurrentStatus;
                if (mCurrentStatus) {
                    item.setIcon(R.mipmap.ic_star_white);
                } else {
                    item.setIcon(R.mipmap.ic_star_border);
                }
            } else if (id == R.id.action_share) {

            }
            return true;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gankdetail_toolbar_menu, menu);
        return true;
    }

    @Override
    public void initFavorite(GankBean gankBean) {
        if (gankBean != null) {
            mInitStatus = true;
            mCurrentStatus = true;
        }
    }

    //动态改变item的方式
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_favorite);
        if (mInitStatus) {
            item.setIcon(R.mipmap.ic_star_white);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mInitStatus != mCurrentStatus) {
            if (mCurrentStatus) {
                int result = mGankDetailPresenter.addGank(mContext, mGankBean);
            } else {
                int result = mGankDetailPresenter.delteGank(mContext, mGankBean.id);
            }
        }
        super.onDestroy();
    }
}
