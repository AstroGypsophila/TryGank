package com.gypsophila.trygank.main.presenter;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.main.view.IMainView;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/23
 */
public class MainPresenterImpl implements IMainPresenter {

    private IMainView mMainView;

    public MainPresenterImpl(IMainView mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation1:
                mMainView.switchToNews();
                break;
            case R.id.navigation2:
                mMainView.switchToAbout();
                break;
        }
    }
}
