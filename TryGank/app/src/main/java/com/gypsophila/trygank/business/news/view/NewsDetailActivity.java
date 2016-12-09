package com.gypsophila.trygank.business.news.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppSwipeBackActivity;
import com.gypsophila.trygank.business.news.model.NewsBean;
import com.gypsophila.trygank.business.news.presenter.INewsDetailPresenter;
import com.gypsophila.trygank.business.news.presenter.NewsDetailPresenterImpl;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/11
 */
public class NewsDetailActivity extends AppSwipeBackActivity implements INewsDetaiView {

    private NewsBean news;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private HtmlTextView mNewsContent;
    private INewsDetailPresenter mNewsDetailPresenter;
    private ImageView mNewsDetailImg;
    private Context mContext;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为image延伸至状态栏而设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_newsdetail);
        mContext = this;
        news = (NewsBean) getIntent().getSerializableExtra("news");
        initToolbar();

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(news.getTitle());
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mNewsContent = (HtmlTextView) findViewById(R.id.news_detail_content);
        mNewsDetailImg = (ImageView) findViewById(R.id.news_detail_img);
        ImageLoaderUtils.loadImage(mContext, mNewsDetailImg, news.getImgsrc());
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
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNewsDetail(String content) {
        mNewsContent.setHtmlFromString(content, new HtmlTextView.LocalImageGetter());
    }
}
