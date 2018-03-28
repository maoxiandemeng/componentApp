package com.jing.componentapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jing.componentapp.widget.DrawableCache;
import com.jing.library.base.BaseCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/8/12.
 */

public abstract class BaseActivity extends BaseCompatActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DrawableCache.getInstance().clear();
        bind.unbind();
    }
}
