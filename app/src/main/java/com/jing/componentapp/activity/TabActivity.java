package com.jing.componentapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.base.BaseActivity;
import com.jing.library.tab.CommonTabView;
import com.jing.library.tab.TabAdapter;

import butterknife.BindView;

/**
 * Created by liujing on 2018/2/8.
 */

public class TabActivity extends BaseActivity {

    @BindView(R.id.tab)
    CommonTabView tab;

    private LayoutInflater inflater;
    private String[] strings = {"全部","附近","筛选"};
    private MyTabAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = LayoutInflater.from(this);
        adapter = new MyTabAdapter(strings);
//        adapter = new TabAdapter<String>(strings) {
//            int selectPosition;
//
//            @Override
//            public View getView(CommonTabView parent, String s, int position) {
//                View view = inflater.inflate(R.layout.tab_item, parent, false);
//                TextView tv = view.findViewById(R.id.tv);
//                tv.setText(s);
//                tv.setTextColor(position == selectPosition ? Color.RED : Color.BLACK);
//                return view;
//            }
//
//            public void setSelect(int tab){
//                selectPosition = tab;
//                notifyDataChanged();
//            }
//        };

        tab.setAdapter(adapter);

        tab.setOnTabSelectListener(new CommonTabView.OnTabSelectListener() {
            @Override
            public void onTabSelect(int tab) {
                adapter.setSelect(tab);
            }
        });

    }

    public class MyTabAdapter extends TabAdapter<String> {

        int selectPosition;

        public MyTabAdapter(String[] data) {
            super(data);
        }

        @Override
        public View getView(CommonTabView parent, String s, int position) {
            View view = inflater.inflate(R.layout.tab_item, parent, false);
            TextView tv = view.findViewById(R.id.tv);
            tv.setText(s);
            tv.setTextColor(position == selectPosition ? Color.RED : Color.BLACK);
            return view;
        }

        public void setSelect(int tab){
            selectPosition = tab;
            notifyDataChanged();
        }

    }
}
