package com.jing.componentapp.model;

import com.jing.componentapp.api.ApiRequest;
import com.jing.componentapp.api.GankCallBack;
import com.jing.componentapp.api.GankResult;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.library.http.HttpHelper;
import com.jing.library.utils.Utils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujing on 2017/7/21.
 */

public class HomeModel {

    public void getData(int page, final HomeDataListener listener) {
        ApiRequest service = HttpHelper.getInstance(Utils.getContext()).getService(ApiRequest.class);
        service.getData(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GankCallBack<GankResult<ArrayList<FuLiBean>>>() {
                    @Override
                    public void onSuccess(GankResult<ArrayList<FuLiBean>> model) {
                        listener.onSuccess(model.getResults());
                    }

                    @Override
                    public void onFail(String message) {
                        listener.onFail(message);
                    }
                });
    }

    public interface HomeDataListener{
        void onSuccess(ArrayList<FuLiBean> bean);
        void onFail(String message);
    }
}
