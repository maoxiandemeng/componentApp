package com.jing.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jing.library.utils.LogUtil;

/**
 * Created by liujing on 2017/7/17.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "--------onCreate--------");
        setContentView(getLayoutResId());
    }

    protected abstract int getLayoutResId();

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

    public void openActivity(Class<?> cls){
        openActivity(cls, null, 0);
    }

    public void openActivity(Class<?> cls, Bundle bundle){
        openActivity(cls, bundle, 0);
    }

    public void openActivity(Class<?> cls, int flag){
        openActivity(cls, null, flag);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int flag){
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
