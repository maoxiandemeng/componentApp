package com.jing.library.http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jing.library.http.cookie.SimpleCookieJar;
import com.jing.library.net.NetUtils;
import com.jing.library.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求的基本封装
 */

public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static volatile HttpHelper instance;
    private Context mContext;
    private static final int DEFAULT_TIMEOUT = 60;
    private HashMap<String, Object> mServiceMap;

    public HttpHelper(Context mContext) {
        this.mContext = mContext;
        mServiceMap = new HashMap<>();
    }

    public static HttpHelper getInstance(Context mContext) {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    synchronized (HttpHelper.class) {
                        instance = new HttpHelper(mContext);
                    }
                }
            }
        }
        return instance;
    }

    public <S> S getService(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            S service = createService(serviceClass);
            mServiceMap.put(serviceClass.getName(), service);
            return service;
        }
    }

    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            S service = createService(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), service);
            return service;
        }
    }

    private <S> S createService(Class<S> serviceClass) {
        //证书文件放在res/raw文件夹下
//        int[] certficates = new int[]{R.raw.media};
//        SSLSocketFactory sslSocketFactory = SSLHelper.getSSLSocketFactory(mContext, certficates);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.cookieJar(new SimpleCookieJar());

        File httpCacheDirectory = new File(mContext.getCacheDir(), "okHttpCache");
        httpClient.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
//        httpClient.addNetworkInterceptor(new LogInterceptor());
        httpClient.addInterceptor(new CacheControlInterceptor());
        httpClient.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(TAG, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        //https请求添加证书
//        httpClient.socketFactory(sslSocketFactory);
        //添加cookie信息
//        httpClient.cookieJar(new CookieManger(mContext));

        return createService(serviceClass, httpClient.build());
    }

    private <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        String base_url = "";
        try {
            Field baseField = serviceClass.getField("base_url");
            base_url = (String) baseField.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "createService: 基本路径的属性必须是base_url定义");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "createService: 基本路径的属性必须是base_url定义");
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(MyGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LogUtil.v(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            LogUtil.v(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    /**
     * 设置数据缓存的Interceptor，无网络时也可以返回数据
     */
    private class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (NetUtils.isNetworkConnected()) {
                int maxAge = 60 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

}
