package com.jing.componentapp.router;

import com.jing.library.utils.Utils;
import com.jing.router.JRouter;

/**
 * Created by liujing on 2017/7/17.
 */

public class ActivitySchemeOpen {
    private static volatile ActivitySchemeOpen instance;
    private final ActivityRouter router;

    public ActivitySchemeOpen() {
        router = JRouter.getInstance(Utils.getContext()).create(ActivityRouter.class);
    }

    public static ActivitySchemeOpen getInstance() {
        if (instance == null) {
            synchronized (ActivitySchemeOpen.class) {
                if (instance == null) {
                    instance = new ActivitySchemeOpen();
                }
            }
        }
        return instance;
    }

    public ActivityRouter getRouter() {
        return router;
    }
}
