package com.jing.business_two.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jing.business_two.tools.LogTool;

/**
 * Created by liujing on 2017/7/19.
 */

public abstract class BaseLazyFragment extends BaseFragment {
    //判断界面是否可见
    private boolean isVisible = false;
    //判断是否第一次加载数据
    private boolean isFirst = true;
    //判断是否做了一些初始化的操作
    private boolean isInit = false;

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
        LogTool.i(getClass().getSimpleName(), "lazyData");
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
