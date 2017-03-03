package com.jing.componentapp;

import android.app.Application;

import com.jing.library.utils.Utils;

/**
 * Created by jon on 2017/3/3
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
