package com.jing.business_one.router;

import com.jing.library.utils.Utils;
import com.jing.router.JRouter;

/**
 * Created by liujing on 2017/7/17.
 */

public class OneActivitySchemeOpen {
    private static volatile OneActivitySchemeOpen instance;
    private final OneActivityRouter router;

    public OneActivitySchemeOpen() {
        router = JRouter.getInstance(Utils.getContext()).create(OneActivityRouter.class);
    }

    public static OneActivitySchemeOpen getInstance() {
        if (instance == null) {
            synchronized (OneActivitySchemeOpen.class) {
                if (instance == null) {
                    instance = new OneActivitySchemeOpen();
                }
            }
        }
        return instance;
    }

    public OneActivityRouter getRouter() {
        return router;
    }
}
