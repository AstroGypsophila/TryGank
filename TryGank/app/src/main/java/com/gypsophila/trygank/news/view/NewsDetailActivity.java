package com.gypsophila.trygank.news.view;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.news.model.NewsBean;
import com.gypsophila.trygank.news.presenter.INewsDetailPresenter;
import com.gypsophila.trygank.news.presenter.NewsDetailPresenterImpl;
import com.gypsophila.trygank.utils.ToolsUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/11
 */
public class NewsDetailActivity extends SwipeBackActivity implements INewsDetaiView{

    private NewsBean news;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mContentTv;
    private INewsDetailPresenter mNewsDetailPresenter;
    private ImageView mNewsDetailImg;
    private Context mContext;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_newsdetail);
        mContext = this;
        news = (NewsBean) getIntent().getSerializableExtra("news");
        initToolbar();

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(news.getTitle());
        mContentTv = (TextView) findViewById(R.id.news_detail_content);
        mNewsDetailImg = (ImageView) findViewById(R.id.news_detail_img);
        ImageLoaderUtils.loadImage(mContext,mNewsDetailImg,news.getImgsrc());
        mNewsDetailPresenter = new NewsDetailPresenterImpl(this);
        mNewsDetailPresenter.loadNewsDetail(mContext, news.getDocid(), null, true);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showNewsDetail(String content) {
        mContentTv.setText(content);
    }
}
