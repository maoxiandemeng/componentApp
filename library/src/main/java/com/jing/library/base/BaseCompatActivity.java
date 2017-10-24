package com.jing.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.jing.library.R;
import com.jing.library.utils.LogUtil;

/**
 * Created by liujing on 2017/7/17.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "--------onCreate--------");
        setContentView(getLayoutResId());
    }

    protected abstract int getLayoutResId();

    protected void initToolbar(Toolbar toolbar, String title) {
        if (TextUtils.isEmpty(title)) {
            toolbar.setTitle(getResources().getString(R.string.app_name));
        } else {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
    }

    protected void initToolbarBack(Toolbar toolbar, String title) {
        initToolbar(toolbar, title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "--------onRestart--------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "--------onStart--------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "--------onResume--------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "--------onPause--------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "--------onStop--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "--------onDestroy--------");
    }

    public void openActivity(Class<?> cls) {
        openActivity(cls, null, 0);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, 0);
    }

    public void openActivity(Class<?> cls, int flag) {
        openActivity(cls, null, flag);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int flag) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flag != 0) {
            intent.setFlags(flag);
        }
        startActivity(intent);
    }
}
