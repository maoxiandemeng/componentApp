package com.jing.componentapp.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.HomeAdapter;
import com.jing.componentapp.base.BaseLazyFragment;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.library.refresh.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liujing on 2017/7/19.
 */

public class ShopFragment extends BaseLazyFragment implements OnRefreshListener, OnLoadmoreListener{
//    @BindView(R.id.tool_bar)
//    Toolbar toolbar;
//    @BindView(R.id.bar_name)
//    TextView barName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private HomeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void init() {
//        barName.setText("商店");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
//        refreshLayout.setRefreshHeader(new MaterialHeader(activity));
//        refreshLayout.setRefreshFooter(new BallPulseFooter(activity));

        adapter = new HomeAdapter(activity, new ArrayList<FuLiBean>());
        recyclerView.setAdapter(adapter);

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
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
//        final ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            strings.add(String.valueOf(i));
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.clearAddData(strings);
//                refreshLayout.finishRefresh();
//            }
//        }, 2000);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        final ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            strings.add(String.valueOf(i));
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.addData(strings);
//                refreshLayout.finishLoadmore();
//            }
//        }, 2000);

    }


}
