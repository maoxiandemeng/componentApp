package com.jing.componentapp.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jing.componentapp.R;
import com.jing.componentapp.activity.SpacesItemDecoration;
import com.jing.componentapp.adapter.ShopAdapter;
import com.jing.library.base.BaseLazyFragment;
import com.jing.library.utils.Helper;
import com.jing.library.utils.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/7/19.
 */

public class ShopFragment extends BaseLazyFragment implements OnRefreshListener, OnLoadmoreListener{
    private Unbinder bind;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private ShopAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void init() {
        bind = ButterKnife.bind(this, contentView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ShopAdapter(activity, null);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(Helper.dp2px(8)));

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    protected void onInVisible() {
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            }
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore();
            }
        }
    }

    @Override
    protected void lazyData() {
        LogUtil.i(TAG, "lazyData: ");
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        final ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(String.valueOf(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clearAddData(strings);
                refreshLayout.finishRefresh();
            }
        }, 2000);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        final ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(String.valueOf(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData(strings);
                refreshLayout.finishLoadmore();
            }
        }, 2000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
