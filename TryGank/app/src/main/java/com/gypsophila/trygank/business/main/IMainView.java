package com.gypsophila.trygank.business.main;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/8/23
 */
public interface IMainView {

    void switchNavigation(int id);

    void switchToXiandu();

    void switchToGank();

    void switchToSearch();

    void switchToNotification();

    void switchToFavorite();

    void switchToToday();
}
