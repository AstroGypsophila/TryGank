package com.gypsophila.trygank.utils;

import android.app.ProgressDialog;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.commonlib.utils.BaseUtils;

/**
 * Created by Gypsophila on 2016/8/1.
 */
public class Utils extends BaseUtils {

    /**
     * 将对象转化为整数数字类型
     * @param object
     * @param defaultValue
     * @return
     */
    public static final int convertToInt(Object object, int defaultValue) {
        if (null == object || "".equals(object.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(object.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(object.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }


    /**
     *
     * @param activity
     * @param message
     * @return
     */
    public static ProgressDialog createProgressDialog(final BaseActivity activity, final String message) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
