package com.jing.library.utils;

import android.util.Log;

import com.jing.library.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liujing on 2017/9/6.
 */

public class LogUtil {
    private static final String TAG = "LogUtil";
    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;

    private static final int JSON = 0x7;

    public static void v(String message) {
        printLog(V, TAG, message);
    }

    public static void v(String tag, String message) {
        printLog(V, tag, message);
    }

    public static void d(String message) {
        printLog(D, TAG, message);
    }

    public static void d(String tag, String message) {
        printLog(D, tag, message);
    }

    public static void i(String message) {
        printLog(I, TAG, message);
    }

    public static void i(String tag, String message) {
        printLog(I, tag, message);
    }

    public static void w(String message) {
        printLog(W, TAG, message);
    }

    public static void w(String tag, String message) {
        printLog(W, tag, message);
    }

    public static void e(String message) {
        printLog(E, TAG, message);
    }

    public static void e(String tag, String message) {
        printLog(E, tag, message);
    }

    public static void json(String message) {
        printLog(JSON, TAG, message);
    }

    public static void json(String tag, String message) {
        printLog(JSON, tag, message);
    }


    public static void printLog(int type, String tag, String message) {
        //在Debug模式下才会打印日志
        if (!BuildConfig.DEBUG) {
            return;
        }
        switch (type) {
            case V:
                Log.v(tag, message);
                break;
            case D:
                Log.d(tag, message);
                break;
            case I:
                Log.i(tag, message);
                break;
            case W:
                Log.w(tag, message);
                break;
            case E:
                Log.e(tag, message);
                break;
            case JSON:
                printJson(tag, message);
                break;

        }
    }

    public static void printJson(String tag, String message) {
        String json;
        try {
            if (message.startsWith("{")) {
                JSONObject object = new JSONObject(message);
                json = object.toString(4);
            } else if (message.startsWith("[")) {
                JSONArray array = new JSONArray(message);
                json = array.toString(4);
            } else {
                json = message;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            json = message;
        }
        i(tag, "start ==================================================");
        String[] split = json.split(System.getProperty("line.separator"));
        for (String line : split) {
            i(tag, line);
        }
        i(tag, " ================================================== end");
    }
}
