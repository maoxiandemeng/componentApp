package com.jing.componentapp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.CartAdapter;
import com.jing.library.base.BaseLazyFragment;
import com.jing.library.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        JSONObject object = new JSONObject();
        try {
            object.put("name","jon");
            object.put("age","26");
            object.put("address","上海");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.printJson(TAG, object.toString());

        JSONArray array = new JSONArray();
        array.put(object);
        array.put(object);
        array.put(object);
        LogUtil.printJson(TAG, array.toString());

        JSONObject object1 = new JSONObject();
        try {
            object1.put("person", object);
            object1.put("array", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.printJson(TAG, object1.toString());
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void lazyData() {
        LogUtil.i(TAG, "lazyData: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
