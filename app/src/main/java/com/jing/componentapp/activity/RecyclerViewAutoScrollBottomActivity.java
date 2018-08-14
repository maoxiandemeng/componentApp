package com.jing.componentapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.RecyclerAutoBottomAdapter;
import com.jing.componentapp.base.BaseActivity;
import com.jing.library.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by liujing on 2018/8/14.
 */
public class RecyclerViewAutoScrollBottomActivity extends BaseActivity {
    private static final String TAG = "RecyclerViewAutoScrollB";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int i;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i++;
            adapter.addData("Auto"+String.valueOf(i));
            LogUtil.i(TAG, "adapter.getItemCount(): "+adapter.getItemCount());
            layoutManager.smoothScrollToPosition(recyclerView, null, adapter.getItemCount());
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };
    private RecyclerAutoBottomAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_auto_scroll_bottom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAutoBottomAdapter(this, null);
        recyclerView.setAdapter(adapter);

        handler.sendEmptyMessage(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
