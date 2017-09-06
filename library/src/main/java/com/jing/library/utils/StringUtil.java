package com.jing.library.utils;

/**
 * Created by liujing on 2017/9/6.
 */

public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "null".equals(str) || str.length() == 0;
    }
}
