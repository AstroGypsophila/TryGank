package com.gypsophila.trygank.activity.gank;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.engine.RemoteService;

/**
 * Created by Gypsophila on 2016/8/8.
 */
public class TestGankApi extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.show();
        RemoteService.getInstance().invoke(this, "Android", null, new AbstractRequestCallback() {
            @Override
            public void onSuccess(String content) {
                dialog.dismiss();
                Log.i("cj_data", "content = " + content);
            }
        });
    }
}
