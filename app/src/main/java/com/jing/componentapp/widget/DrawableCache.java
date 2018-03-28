package com.jing.componentapp.widget;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujing on 2018/3/28.
 */

public class DrawableCache {
    private static volatile DrawableCache instance;
    private Map<String, Drawable> map;

    public DrawableCache() {
        map = new HashMap<>();
    }

    public static DrawableCache getInstance() {
        if (instance == null) {
            synchronized (DrawableCache.class) {
                if (instance == null) {
                    instance = new DrawableCache();
                }
            }
        }
        return instance;
    }

    public void set(String url, Drawable drawable) {
        map.put(url, drawable);
    }

    public Drawable get(String url) {
        return map.get(url);
    }

    public boolean hasKey(String url){
        return map.containsKey(url);
    }

    public void clear(){
        if (map != null) {
            map.clear();
        }
    }
}
