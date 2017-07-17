package com.jing.business_two;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jing.business_two.router.TwoActivitySchemeOpen;
import com.jing.library.base.BaseCompatActivity;

import butterknife.BindView;

public class TwoMainActivity extends BaseCompatActivity {

    @BindView(R2.id.two_test)
    TextView twoTest;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_two;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
