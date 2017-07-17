package com.jing.componentapp.router;

import com.jing.router.annotation.RouterParam;
import com.jing.router.annotation.RouterUri;

/**
 * Created by liujing on 2017/7/17.
 */

public interface ActivityRouter {

    @RouterUri(routerUri = "business_one://main")
    void openOne(@RouterParam("one") String one);
}
