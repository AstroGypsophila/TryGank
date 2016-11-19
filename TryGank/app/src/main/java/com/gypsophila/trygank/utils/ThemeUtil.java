package com.gypsophila.trygank.utils;

import android.app.Activity;
import android.content.Context;

import com.gypsophila.commonlib.utils.PreferenceUtils;
import com.gypsophila.resmodule.R;
import com.gypsophila.trygank.engine.AppConstants;

/**
 * created by lguipeng
 */
public class ThemeUtil {

    public static void changTheme(Activity activity, Theme theme) {
        if (activity == null)
            return;
        int style = R.style.DefaultTheme;
        switch (theme) {
            case AMBER:
                style = R.style.AmberTheme;
                break;
            case BLUE:
                style = R.style.BlueTheme;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyTheme;
                break;
            case BROWN:
                style = R.style.BrownTheme;
                break;
            case CYAN:
                style = R.style.CyanTheme;
                break;
            case GREY:
                style = R.style.GreyTheme;
                break;
            case INDIGO:
                style = R.style.IndigoTheme;
                break;
            case LIGHT_GREEN:
                style = R.style.LightGreenTheme;
                break;
            case PINK:
                style = R.style.PinkTheme;
                break;
            case PURPLE:
                style = R.style.PurpleTheme;
                break;
            case RED:
                style = R.style.RedTheme;
                break;
            case YELLOW:
                style = R.style.YellowTheme;
                break;
            default:
                break;
        }
        activity.setTheme(style);
    }

    public static Theme getCurrentTheme(Context ctx) {
        int value = PreferenceUtils.getInteger(ctx, AppConstants.THEME_VALUE, 0);
        return ThemeUtil.Theme.mapValueToTheme(value);
    }

    public enum Theme {
        AMBER(0),
        BLUE(1),
        BLUE_GREY(2),
        BROWN(3),
        CYAN(4),
        GREY(5),
        INDIGO(6),
        LIGHT_GREEN(7),
        PINK(8),
        PURPLE(9),
        RED(10),
        YELLOW(11);
        private int mValue;

        Theme(int value) {
            this.mValue = value;
        }

        public static Theme mapValueToTheme(final int value) {
            for (Theme theme : Theme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return AMBER;
        }

        static Theme getDefault() {
            return AMBER;
        }

        public int getIntValue() {
            return mValue;
        }
    }
}
