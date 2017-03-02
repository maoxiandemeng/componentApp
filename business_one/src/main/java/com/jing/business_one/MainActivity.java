package com.jing.business_one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jing.router.ActivityRouter;
import com.jing.router.JRouter;

public class MainActivity extends AppCompatActivity {

    private ActivityOpen activityOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        String one = getIntent().getData().getQueryParameter("one");
        activityOpen = JRouter.getInstance(this).create(ActivityOpen.class);
        TextView textView = (TextView) findViewById(R.id.extra);
        textView.setText("业务"+one);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityRouter.openActivity(MainActivity.this, "business_two://main?two=two test");
                activityOpen.openTwo("two test");
            }
        });
    }
}
