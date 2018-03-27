package com.jing.componentapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jing.library.base.BaseCompatFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/7/19.
 */

public abstract class BaseLazyFragment extends BaseCompatFragment {
    //判断界面是否可见
    private boolean isVisible = false;
    //判断是否第一次加载数据
    private boolean isFirst = true;
    //判断是否做了一些初始化的操作
    private boolean isInit = false;
    private Unbinder bind;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        isInit = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }

    /**
     * 在界面不可见时做一些初始化的操作
     */
    protected abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    private void onVisible() {
        if (!isVisible || !isFirst || !isInit) {
            return;
        }
        lazyData();
        isFirst = false;
    }

    /**
     * 界面不可见时执行的方法
     */
    protected abstract void onInVisible();

    /**
     * 界面可见时执行的方法
     */
    protected abstract void lazyData();
}
