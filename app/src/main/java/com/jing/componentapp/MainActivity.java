package com.jing.componentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jing.componentapp.router.ActivitySchemeOpen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView test = (TextView) findViewById(R.id.test);
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
