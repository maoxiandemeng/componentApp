package com.jing.componentapp.proxy;

import android.view.View;

import com.jing.library.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by liujing on 2018/3/19.
 */

public class ProxyOnClick {
    private ProxyParam defaultOnClick;

    public ProxyOnClick(ProxyParam defaultOnClick) {
        this.defaultOnClick = defaultOnClick;
    }

    public View.OnClickListener create() {
        return (View.OnClickListener) Proxy.newProxyInstance(defaultOnClick.getClass().getClassLoader(), defaultOnClick.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                LogUtil.i(defaultOnClick.getStr());
                method.invoke(defaultOnClick, objects);
                return null;
            }
        });
    }
}
