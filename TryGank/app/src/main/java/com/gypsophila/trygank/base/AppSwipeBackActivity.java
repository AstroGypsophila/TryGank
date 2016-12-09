package com.gypsophila.trygank.base;

import android.os.Bundle;

import com.gypsophila.commonlib.utils.PreferenceUtils;
import com.gypsophila.trygank.umeng.UmengEvent;
import com.gypsophila.trygank.utils.ThemeUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/11/16
 */
public abstract class AppSwipeBackActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtil.changTheme(this, ThemeUtil.getCurrentTheme(this));
        super.onCreate(savedInstanceState);
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
