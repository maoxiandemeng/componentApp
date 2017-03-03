package com.jing.library.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by jon on 2017/3/3
 */

public class ToastUtils {
    private static Toast toast;

    public static void showLongToast(String text){
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showShortToast(String text){
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(@StringRes int resId){
        showToast(Utils.getContext().getString(resId), Toast.LENGTH_LONG);
    }

    public static void showShortToast(@StringRes int resId){
        showToast(Utils.getContext().getString(resId), Toast.LENGTH_SHORT);
    }

    private static void showToast(String text, int duration){
        if (toast == null) {
            toast = Toast.makeText(Utils.getContext(), text, duration);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
