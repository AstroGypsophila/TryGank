package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppSwipeBackActivitiy;
import com.gypsophila.trygank.listener.SearchTextWatcher;
import com.gypsophila.trygank.systemevent.FinishInputEvent;
import com.gypsophila.trygank.utils.ToolsUtil;

import org.greenrobot.eventbus.EventBus;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/6
 */
public class SearchActivity extends AppSwipeBackActivitiy {

    private ImageView mBackIv;
    private EditText mSearchEt;
    private SwipeBackLayout mSwipeBackLayout;
    private Context mContext;
    private InputMethodManager mInputManager;
    private TextWatcher mTextWatcher = new SearchTextWatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search);
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initViews();
    }

    private void initViews() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mBackIv = (ImageView) findViewById(R.id.back_iv);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchEt = (EditText) findViewById(R.id.search_et);
        if (mInputManager != null) {
            mSearchEt.requestFocus();
            mInputManager.showSoftInput(mSearchEt, 0);
        }
        mSearchEt.addTextChangedListener(mTextWatcher);
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean bool = false;
                //如果是搜索动作
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(mSearchEt.getText().toString())) {
                        EventBus.getDefault().post(new FinishInputEvent(mSearchEt.getText().toString()));
                        bool = true;
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_content, SearchListFragment.newInstance(), SearchListFragment.TAG)
                                .commit();
                    }
                    if (mInputManager != null) {
                        mInputManager.hideSoftInputFromWindow(mSearchEt.getWindowToken(), 0);
                    }
                }
                return bool;
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.search_content, SearchListFragment.newInstance(), SearchListFragment.TAG)
                .commit();

    }
}
