package com.jing.componentapp.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.HomeAdapter;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.componentapp.presenter.HomePresenter;
import com.jing.componentapp.router.ActivitySchemeOpen;
import com.jing.componentapp.view.HomeView;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.adapter.listener.OnRecyclerItemClickListener;
import com.jing.library.base.BaseLazyFragment;
import com.jing.library.divider.DividerGridItemDecoration;
import com.jing.library.utils.LogUtil;
import com.jing.library.utils.ToastUtils;
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

public class HomeFragment extends BaseLazyFragment implements OnRefreshListener, OnLoadmoreListener, HomeView {
    private Unbinder bind;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private HomeAdapter adapter;

    private int page = 1;
    private HomePresenter presenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        bind = ButterKnife.bind(this, contentView);
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(activity, null);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(activity, R.drawable.divider_grid_8));

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);

        presenter = new HomePresenter(this);

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

        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void setOnRecyclerItemClick(BaseViewHolder viewHolder, BaseRecyclerAdapter adapter, int position) {
                ActivitySchemeOpen.getInstance().getRouter().openOne("one");
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        presenter.getData(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        presenter.getData(page);
    }

    @Override
    public void showContent(ArrayList<FuLiBean> bean) {
        if (page == 1) {
            adapter.clearAddData(bean);
        } else {
            adapter.addData(bean);
        }
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }

    }

    @Override
    public void onFail(String message) {
        ToastUtils.showShortToast(message);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
