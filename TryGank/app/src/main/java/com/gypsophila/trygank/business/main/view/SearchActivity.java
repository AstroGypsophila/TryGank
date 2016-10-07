package com.gypsophila.trygank.business.main.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.utils.ToolsUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/6
 */
public class SearchActivity extends SwipeBackActivity {

    private ImageView mBackIv;
    private EditText mSearchEt;
    private SwipeBackLayout mSwipeBackLayout;
    private Context mContext;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search);
        initViews();
    }

    private void initViews() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mBackIv = (ImageView) findViewById(R.id.back_iv);
        mSearchEt = (EditText) findViewById(R.id.search_et);
//        mRecyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);

    }
}
