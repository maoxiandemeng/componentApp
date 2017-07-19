package com.jing.business_two.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jing.business_two.tools.LogTool;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujing on 2017/7/17.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        LogTool.d(TAG, "onCreate");
        bind = ButterKnife.bind(this);
    }

    protected abstract int getLayoutResId();

    @Override
    protected void onRestart() {
        super.onRestart();
        LogTool.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogTool.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogTool.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogTool.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogTool.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogTool.d(TAG, "onDestroy");
        bind.unbind();
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
