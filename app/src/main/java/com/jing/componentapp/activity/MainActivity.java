package com.jing.componentapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.DrawerAdapter;
import com.jing.componentapp.adapter.MainPagerAdapter;
import com.jing.componentapp.base.BaseActivity;
import com.jing.componentapp.fragment.CartFragment;
import com.jing.componentapp.fragment.HomeFragment;
import com.jing.componentapp.fragment.ShopFragment;
import com.jing.componentapp.router.ActivitySchemeOpen;
import com.jing.componentapp.rxjava.RxJavaActivity;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.adapter.listener.OnRecyclerItemClickListener;
import com.jing.library.utils.Helper;
import com.jing.library.utils.LogUtil;
import com.jing.library.utils.ToastUtils;
import com.jing.library.viewpager.ScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnRecyclerItemClickListener {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @BindView(R.id.view_pager)
    ScrollViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        Uri data = getIntent().getData();
        if (data != null) {
            String main = data.getQueryParameter("main");
            if (!TextUtils.isEmpty(main)) {
            }
        }
        initFragment();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        ArrayList<String> list = new ArrayList<>();
        list.add("RxJava");
        list.add("横向滚动的RecyclerView");
        list.add(String.valueOf(3));
        list.add(String.valueOf(4));
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, list);
        recyclerView.setAdapter(drawerAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(Helper.dp2px(8)));
        drawerAdapter.setOnRecyclerItemClickListener(this);
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
                        viewPager.setCurrentItem(0);
                        ToastUtils.showShortToast("home");
                        break;
                    case R.id.tab_shop:
                        viewPager.setCurrentItem(1);
                        ToastUtils.showShortToast("shop");
                        break;
                    case R.id.tab_cart:
                        viewPager.setCurrentItem(2);
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

    private void initFragment() {
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(2);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ShopFragment());
        fragments.add(new CartFragment());
        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setOnRecyclerItemClick(BaseViewHolder viewHolder, BaseRecyclerAdapter adapter, int position) {
        switch (position) {
            case 0:
                openActivity(RxJavaActivity.class);
                break;
            case 1:
                openActivity(HRecyclerActivity.class);
                break;
            case 2:
                ActivitySchemeOpen.getInstance().getRouter().openOne("one");
                break;
        }
    }
}
