package com.jing.router;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.jing.router.annotation.RouterParam;
import com.jing.router.annotation.RouterUri;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by jon on 2017/3/2
 */

public class JRouter {
    private static final String TAG = "JRouter";
    private static volatile JRouter instance;
    private Context context;

    public JRouter(Context context) {
        this.context = context.getApplicationContext();
    }

    public static JRouter getInstance(Context context){
        if (instance == null) {
            synchronized (JRouter.class) {
                if (instance == null) {
                    instance = new JRouter(context);
                }
            }
        }
        return instance;
    }

    public <T> T create(final Class<T> service){

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        StringBuilder sb = new StringBuilder();
                        RouterUri routerUri = method.getAnnotation(RouterUri.class);
                        Log.d(TAG, "routerUri: " + routerUri.routerUri());
                        sb.append(routerUri.routerUri());
                        Annotation[][] annotations = method.getParameterAnnotations();
                        int pos = 0;
                        for (int i = 0; i < annotations.length; i++) {
                            Annotation[] annotation = annotations[i];
                            if (annotation != null && annotation.length > 0) {
                                if (pos == 0) {
                                    sb.append("?");
                                } else {
                                    sb.append("&");
                                }
                                pos++;
                                RouterParam param = (RouterParam) annotation[0];
                                sb.append(param.value());
                                sb.append("=");
                                sb.append(args[i]);
                                Log.d(TAG, "param: "+param.value()+"="+args[i]);
                            }
                        }
                        openRouterUri(sb.toString());
                        return null;
                    }
                });
    }

    /**
     * 通过uri跳转指定页面
     *
     * @param url
     */
    private void openRouterUri(String url) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            context.startActivity(intent);
        }
    }
}
