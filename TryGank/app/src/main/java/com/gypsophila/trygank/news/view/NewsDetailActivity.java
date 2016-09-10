package com.gypsophila.trygank.news.view;

import android.os.Bundle;
import android.view.Window;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/11
 */
public class NewsDetailActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_newsdetail);
    }
}
