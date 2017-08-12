package com.jing.business_two;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jing.business_two.router.TwoActivitySchemeOpen;
import com.jing.library.base.BaseCompatActivity;

public class TwoMainActivity extends BaseCompatActivity {

    TextView twoTest;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_two;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twoTest = (TextView) findViewById(R.id.two_test);
        Uri data = getIntent().getData();
        if (data != null) {
            String one = data.getQueryParameter("two");
            twoTest.setText("业务" + one);
        }
        twoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwoActivitySchemeOpen.getInstance().getRouter().openMain("main");
            }
        });
    }


}
