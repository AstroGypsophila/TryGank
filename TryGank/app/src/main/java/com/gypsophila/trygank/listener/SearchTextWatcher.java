package com.gypsophila.trygank.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.gypsophila.trygank.systemevent.FinishInputEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/24
 */
public class SearchTextWatcher implements TextWatcher {



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        EventBus.getDefault().post(new FinishInputEvent(s.toString()));
    }
}
