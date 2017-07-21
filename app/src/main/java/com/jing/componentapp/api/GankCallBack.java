package com.jing.componentapp.api;

import android.text.TextUtils;

import com.jing.library.net.NetUtils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by liujing on 2017/7/21.
 */

public abstract class GankCallBack<M> implements Observer<M> {

    public abstract void onSuccess(M model);
    public abstract void onFail(String message);


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull M model) {
        GankResult<M> result = (GankResult<M>) model;
        if (result.isError()) {
            onFail("返回失败");
        } else {
            onSuccess(model);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg;
        if (e instanceof SocketTimeoutException) {
            msg = "连接超时";
        } else if (e instanceof SocketException) {
            msg = "连接失败";
        } else if (e instanceof ConnectException) {
            msg = "连接异常";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
        } else {
            msg = "未知错误";
        }
        if (!NetUtils.isNetworkConnected()) {
            msg = "请检查网络连接";
        }
        if (!TextUtils.isEmpty(msg)) {
            onFail(msg);
        }
    }

    @Override
    public void onComplete() {

    }
}
