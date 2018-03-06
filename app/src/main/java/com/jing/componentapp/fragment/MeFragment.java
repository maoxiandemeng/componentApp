package com.jing.componentapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jing.componentapp.R;
import com.jing.componentapp.activity.DownLoadActivity;
import com.jing.componentapp.activity.TabActivity;
import com.jing.componentapp.activity.VideoPlayActivity;
import com.jing.library.base.BaseLazyFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/10/24.
 */

public class MeFragment extends BaseLazyFragment {
    private Unbinder bind;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        bind = ButterKnife.bind(this, contentView);
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void lazyData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }


    @OnClick({R.id.btn_download, R.id.btn_look, R.id.video_play, R.id.btn_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                openActivity(DownLoadActivity.class);
                break;
            case R.id.btn_look:
                break;
            case R.id.video_play:
                openActivity(VideoPlayActivity.class);
                break;
            case R.id.btn_tab:
                openActivity(TabActivity.class);
                break;
        }
    }
}
