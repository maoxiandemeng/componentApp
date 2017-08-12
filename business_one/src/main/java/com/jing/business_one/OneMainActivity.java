package com.jing.business_one;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jing.business_one.base.BaseCompatActivity;
import com.jing.business_one.router.OneActivitySchemeOpen;

public class OneMainActivity extends BaseCompatActivity {

    TextView extra;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_one;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extra = (TextView) findViewById(R.id.extra);
        Uri data = getIntent().getData();
        if (data != null) {
            String one = data.getQueryParameter("one");
            extra.setText("业务" + one);
        }


        extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneActivitySchemeOpen.getInstance().getRouter().openOne("two_test");
            }
        });
    }


}
