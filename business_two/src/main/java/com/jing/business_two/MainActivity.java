package com.jing.business_two;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Uri uri = getIntent().getData();


        TextView twoTest = (TextView) findViewById(R.id.two_test);
        twoTest.setText(uri.getQueryParameter("two"));
    }
}
