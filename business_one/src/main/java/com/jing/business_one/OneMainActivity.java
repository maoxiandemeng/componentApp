package com.jing.business_one;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jing.business_one.router.OneActivitySchemeOpen;

public class OneMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        TextView textView = (TextView) findViewById(R.id.extra);
        Uri data = getIntent().getData();
        if (data != null) {
            String one = data.getQueryParameter("one");
            textView.setText("业务"+one);
        }



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneActivitySchemeOpen.getInstance().getRouter().openOne("two_test");
            }
        });
    }
}
