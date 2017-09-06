package com.jing.componentapp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.CartAdapter;
import com.jing.library.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/7/19.
 */

public class CartFragment extends BaseLazyFragment {

    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    private Unbinder bind;
    private CartAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init() {
        bind = ButterKnife.bind(this, contentView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerCart.setLayoutManager(layoutManager);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 1) {
                list.add("[上海市] 梅陇三部的陈小平 [ 13022178512 ] 正在派件 ");
            } else {
                list.add(String.valueOf(i));
            }
        }
        adapter = new CartAdapter(activity, list);
        recyclerCart.setAdapter(adapter);
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
}
