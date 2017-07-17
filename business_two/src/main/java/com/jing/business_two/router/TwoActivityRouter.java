package com.jing.business_two.router;

import com.jing.router.annotation.RouterParam;
import com.jing.router.annotation.RouterUri;

/**
 * Created by liujing on 2017/7/17.
 */

public interface TwoActivityRouter {

    @RouterUri(routerUri = "app://main")
    void openMain(@RouterParam("main") String main);
}
