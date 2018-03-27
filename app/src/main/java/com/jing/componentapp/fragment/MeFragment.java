package com.jing.componentapp.fragment;

import android.view.View;

import com.jing.componentapp.R;
import com.jing.componentapp.activity.DownLoadActivity;
import com.jing.componentapp.activity.TabActivity;
import com.jing.componentapp.activity.VideoPlayActivity;
import com.jing.componentapp.base.BaseLazyFragment;

import butterknife.OnClick;

/**
 * Created by liujing on 2017/10/24.
 */

public class MeFragment extends BaseLazyFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void lazyData() {

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
