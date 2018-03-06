package com.jing.library.utils;

import android.content.res.Configuration;

/**
 * Created by liujing on 2018/3/6.
 */
public class ScreenUtils {

    /**
     * 获得屏幕的密度
     *
     * @return 0.75/1/1.5/2
     */
    public static float getDensity() {
        return Utils.getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth() {
        return Utils.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight() {
        return Utils.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断是否是pad
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad() {
        return (Utils.getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
