package com.jing.componentapp.view;

import com.jing.componentapp.bean.FuLiBean;

import java.util.ArrayList;

/**
 * Created by liujing on 2017/7/21.
 */

public interface HomeView {
    void showContent(ArrayList<FuLiBean> bean);
    void onFail(String message);
}
