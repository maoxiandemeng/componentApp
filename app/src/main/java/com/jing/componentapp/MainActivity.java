package com.jing.componentapp;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jing.componentapp.router.ActivitySchemeOpen;
import com.jing.library.base.BaseCompatActivity;

import butterknife.BindView;

public class MainActivity extends BaseCompatActivity {

    @BindView(R.id.test)
    TextView test;

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
                test.setText(main);
            }
        }

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySchemeOpen.getInstance().getRouter().openOne("one");
            }
        });
    }

}
