package com.gypsophila.trygank.business.main.presenter;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.main.view.IMainView;

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
            case R.id.item_gank:
                mMainView.switchToGank();
                break;
            case R.id.today:
                mMainView.switchToToday();
                break;
            case R.id.navigation2:
                mMainView.switchToNews();
                break;
            case R.id.favorite:
                mMainView.switchToFavorite();
                break;
        }
    }
}
