package com.jing.library.utils;

import android.content.Context;

/**
 * Created by jon on 2017/3/3
 */

public class Utils {
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("can't init me");
    }

    /**
     * 初始化Context
     * @param context
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 返回得到的ApplicationContext
     * @return
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("you should init first");
    }
}
