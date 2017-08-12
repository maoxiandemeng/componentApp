package com.jing.componentapp.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jing.componentapp.R;
import com.jing.componentapp.activity.SpacesItemDecoration;
import com.jing.componentapp.adapter.HomeAdapter;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.componentapp.presenter.HomePresenter;
import com.jing.componentapp.router.ActivitySchemeOpen;
import com.jing.componentapp.view.HomeView;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.adapter.listener.OnRecyclerItemClickListener;
import com.jing.library.base.BaseLazyFragment;
import com.jing.library.utils.Helper;
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
//    @BindView(R.id.tool_bar)
//    Toolbar toolbar;
//    @BindView(R.id.bar_name)
//    TextView barName;
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
//        toolbar.setNavigationIcon(R.drawable.icon_home);
//        barName.setText("首页");
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(Helper.dp2px(8)));

        adapter = new HomeAdapter(activity, new ArrayList<FuLiBean>());
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);

        presenter = new HomePresenter(this);

        refreshLayout.autoRefresh();
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
