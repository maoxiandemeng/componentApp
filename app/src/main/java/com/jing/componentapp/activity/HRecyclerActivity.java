package com.jing.componentapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.HRecyclerAdapter;
import com.jing.componentapp.base.BaseActivity;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.componentapp.presenter.HomePresenter;
import com.jing.componentapp.view.HomeView;
import com.jing.library.divider.DividerGridItemDecoration;
import com.jing.library.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liujing on 2017/9/6.
 */

public class HRecyclerActivity extends BaseActivity implements HomeView {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private HRecyclerAdapter adapter;

    private HomePresenter presenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hrecycler;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarBack(toolbar, "横向RecyclerView");

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HRecyclerAdapter(this, null);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this, DividerGridItemDecoration.HORIZONTAL, R.drawable.divider_grid_8));

        presenter = new HomePresenter(this);
        presenter.getData(1);

    }

    @Override
    public void showContent(ArrayList<FuLiBean> bean) {
        adapter.addData(bean);
    }

    @Override
    public void onFail(String message) {
        ToastUtils.showShortToast(message);
    }
}
