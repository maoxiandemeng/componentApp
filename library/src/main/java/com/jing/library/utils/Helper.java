package com.jing.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Pattern;

/**
 */

public class Helper {
    //手机号的正则表达式
    public static final String REGEX_MOBILE_EXACT = "^(1[3-9])\\d{9}$";

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
    public static int px2dp( float pxValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置部分文字的颜色
     *
     * @param context 上下文
     * @param str 传入的字符串
     * @param start 开始位置
     * @param end 结束位置
     * @param id  文字颜色
     * @return
     */
    public static SpannableStringBuilder setSpanCorlorString(Context context, String str, int start, int end, int id) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(id)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 设置部分文字的大小
     *
     * @param context 上下文
     * @param str 传入的字符串
     * @param start 开始位置
     * @param end 结束位置
     * @param id  文字大小
     * @return
     */
    public static SpannableStringBuilder setSpanSizeString(Context context, String str, int start, int end, int id) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(id)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder setSpanSizeAndColorString(Context context, String str, int start, int end, int dimenId, int colorId) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(dimenId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 判断字符串是否是手机号码
     * @param phone
     * @return 是true 否false
     */
    public static boolean isMatchPhone(String phone){
        return  isMatch(REGEX_MOBILE_EXACT, phone);
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(Utils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
