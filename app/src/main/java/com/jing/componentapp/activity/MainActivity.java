package com.jing.componentapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.DrawerAdapter;
import com.jing.componentapp.utils.LogUtil;
import com.jing.library.base.BaseCompatActivity;
import com.jing.library.utils.ToastUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            String main = data.getQueryParameter("main");
            if (!TextUtils.isEmpty(main)) {
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(String.valueOf(i));
        }
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, list);
        recyclerView.setAdapter(drawerAdapter);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //抽屉滑动时调用
                LogUtil.i("onDrawerSlide", "slideOffset:" + slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //抽屉打开时调用
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //抽屉关闭时调用
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //抽屉状态改变时调用
            }
        });

//      ActivitySchemeOpen.getInstance().getRouter().openOne("one");
//      drawerLayout.openDrawer(GravityCompat.START);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        ToastUtils.showShortToast("home");
                        break;
                    case R.id.tab_shop:
                        ToastUtils.showShortToast("shop");
                        break;
                    case R.id.tab_cart:
                        ToastUtils.showShortToast("cart");
                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        ToastUtils.showShortToast("home2");
                        break;
                    case R.id.tab_shop:
                        ToastUtils.showShortToast("shop2");
                        break;
                    case R.id.tab_cart:
                        ToastUtils.showShortToast("cart2");
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
