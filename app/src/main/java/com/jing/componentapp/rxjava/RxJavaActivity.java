package com.jing.componentapp.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.jing.componentapp.R;
import com.jing.componentapp.api.ApiRequest;
import com.jing.componentapp.api.GankResult;
import com.jing.componentapp.base.BaseActivity;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.library.http.HttpHelper;
import com.jing.library.utils.LogUtil;
import com.jing.library.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujing on 2017/7/27.
 */

public class RxJavaActivity extends BaseActivity {
    private static final String TAG = "RxJavaActivity";

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar.setTitle("RxJava");
        toolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolBar);

//        RxJavaDemo.demoSix();
//        RxJavaDemo.demoSeven();
        RxJavaDemo.demoEight();

        final ApiRequest service = HttpHelper.getInstance(Utils.getContext()).getService(ApiRequest.class);
        service.getData(2).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<GankResult<ArrayList<FuLiBean>>>() {
                    @Override
                    public void accept(@NonNull GankResult<ArrayList<FuLiBean>> arrayListGankResult) throws Exception {
                        LogUtil.i(TAG, "accept11111111111:  "+ System.currentTimeMillis());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<GankResult<ArrayList<FuLiBean>>, ObservableSource<GankResult<ArrayList<FuLiBean>>>>() {
                    @Override
                    public ObservableSource<GankResult<ArrayList<FuLiBean>>> apply(@NonNull GankResult<ArrayList<FuLiBean>> arrayListGankResult) throws Exception {
                        return service.getData(3);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankResult<ArrayList<FuLiBean>>>() {
                    @Override
                    public void accept(@NonNull GankResult<ArrayList<FuLiBean>> arrayListGankResult) throws Exception {
                        LogUtil.i(TAG, "accept222222222:   "+ System.currentTimeMillis());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtil.i(TAG, "throwable :" + throwable.getMessage()+"::::"+ System.currentTimeMillis());
                    }
                });

    }
}
