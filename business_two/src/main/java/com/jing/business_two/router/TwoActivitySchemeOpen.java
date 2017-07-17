package com.jing.business_two.router;

import com.jing.library.utils.Utils;
import com.jing.router.JRouter;

/**
 * Created by liujing on 2017/7/17.
 */

public class TwoActivitySchemeOpen {
    private static volatile TwoActivitySchemeOpen instance;
    private final TwoActivityRouter router;

    public TwoActivitySchemeOpen() {
        router = JRouter.getInstance(Utils.getContext()).create(TwoActivityRouter.class);
    }

    public static TwoActivitySchemeOpen getInstance() {
        if (instance == null) {
            synchronized (TwoActivitySchemeOpen.class) {
                if (instance == null) {
                    instance = new TwoActivitySchemeOpen();
                }
            }
        }
        return instance;
    }

    public TwoActivityRouter getRouter() {
        return router;
    }
}
