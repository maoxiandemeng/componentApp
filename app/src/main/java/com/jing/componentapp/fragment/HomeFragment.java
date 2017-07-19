package com.jing.componentapp.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.HomeAdapter;
import com.jing.componentapp.base.BaseLazyFragment;
import com.jing.componentapp.router.ActivitySchemeOpen;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.adapter.listener.OnRecyclerItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liujing on 2017/7/19.
 */

public class HomeFragment extends BaseLazyFragment implements OnRefreshListener, OnLoadmoreListener {
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
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
//        toolbar.setNavigationIcon(R.drawable.icon_home);
//        barName.setText("首页");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(activity, new ArrayList<String>());
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

        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void setOnRecyclerItemClick(BaseViewHolder viewHolder, BaseRecyclerAdapter adapter, int position) {
                ActivitySchemeOpen.getInstance().getRouter().openOne("one");
            }
        });
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
}
