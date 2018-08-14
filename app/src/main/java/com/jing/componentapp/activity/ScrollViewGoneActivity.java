package com.jing.componentapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.base.BaseActivity;
import com.jing.library.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liujing on 2018/8/4.
 */
public class ScrollViewGoneActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private boolean show = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scroll_view_gone;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarBack(toolbar, "ScrollViewGone");
        scrollView.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                scrollView.smoothScrollTo(0, 500);
                ToastUtils.showShortToast("500");
                break;
            case R.id.tv2:
                tv2.setText("scrollView getSY: " + scrollView.getScrollY());
                scrollView.setVisibility(View.VISIBLE);
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, 500);
                    }
                }, 100);
                break;
        }
    }
}
