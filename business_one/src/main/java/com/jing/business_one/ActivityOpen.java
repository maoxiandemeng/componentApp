package com.jing.business_one;

import com.jing.router.annotation.RouterParam;
import com.jing.router.annotation.RouterUri;

/**
 * Created by jon on 2017/3/2
 */

public interface ActivityOpen {

    @RouterUri(routerUri = "business_two://main")
    public void openTwo(@RouterParam("two") String two);
}
