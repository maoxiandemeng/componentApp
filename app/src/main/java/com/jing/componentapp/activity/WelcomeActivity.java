package com.jing.componentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.jing.componentapp.R;
import com.jing.componentapp.base.BaseActivity;

/**
 * Created by liujing on 2017/7/17.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
