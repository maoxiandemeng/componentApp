package com.jing.componentapp.presenter;

import com.jing.componentapp.bean.FuLiBean;
import com.jing.componentapp.model.HomeModel;
import com.jing.componentapp.view.HomeView;

import java.util.ArrayList;

/**
 * Created by liujing on 2017/7/21.
 */

public class HomePresenter implements HomeModel.HomeDataListener {
    private HomeModel model;
    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
        model = new HomeModel();
    }

    public void getData(int page){
        model.getData(page, this);
    }

    @Override
    public void onSuccess(ArrayList<FuLiBean> bean) {
        view.showContent(bean);
    }

    @Override
    public void onFail(String message) {
        view.onFail(message);
    }
}
