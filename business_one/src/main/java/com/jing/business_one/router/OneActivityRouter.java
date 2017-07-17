package com.jing.business_one.router;

import com.jing.router.annotation.RouterParam;
import com.jing.router.annotation.RouterUri;

/**
 * Created by liujing on 2017/7/17.
 */

public interface OneActivityRouter {

    @RouterUri(routerUri = "business_two://main")
    void openOne(@RouterParam("two") String two);
}
