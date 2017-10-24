package com.jing.componentapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jing.componentapp.R;
import com.jing.componentapp.adapter.ListDownTimeAdapter;
import com.jing.componentapp.base.BaseActivity;
import com.jing.componentapp.bean.DownTimeBean;
import com.jing.library.divider.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by liujing on 2017/9/30.
 */

public class ListDownTimeActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private ListDownTimeAdapter adapter;
    private ArrayList<DownTimeBean> list = new ArrayList<>();
    private MyThread timeThread;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list_down_time;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarBack(toolbar, "列表倒计时");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListDownTimeAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_8, false));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        initData();
    }

    private void initData() {
        DownTimeBean downTimeBean1 = new DownTimeBean("2017-09-30 18:32:00");
        DownTimeBean downTimeBean2 = new DownTimeBean("2017-09-30 18:45:00");
        DownTimeBean downTimeBean3 = new DownTimeBean("2017-09-30 19:32:00");
        DownTimeBean downTimeBean4 = new DownTimeBean("2017-09-30 18:52:00");
        DownTimeBean downTimeBean5 = new DownTimeBean("2017-09-30 18:38:00");
        DownTimeBean downTimeBean6 = new DownTimeBean("2017-09-30 19:42:00");
        DownTimeBean downTimeBean7 = new DownTimeBean("2017-09-30 20:32:00");
        DownTimeBean downTimeBean8 = new DownTimeBean("2017-09-30 21:32:00");
        DownTimeBean downTimeBean9 = new DownTimeBean("2017-09-30 20:42:00");
        DownTimeBean downTimeBean10 = new DownTimeBean("2017-09-30 21:32:00");
        DownTimeBean downTimeBean11 = new DownTimeBean("2017-09-30 22:32:00");
        DownTimeBean downTimeBean12 = new DownTimeBean("2017-09-30 23:32:00");
        DownTimeBean downTimeBean13 = new DownTimeBean("2017-09-30 24:32:00");

        list.add(downTimeBean1);
        list.add(downTimeBean2);
        list.add(downTimeBean3);
        list.add(downTimeBean4);
        list.add(downTimeBean5);
        list.add(downTimeBean6);
        list.add(downTimeBean7);
        list.add(downTimeBean8);
        list.add(downTimeBean9);
        list.add(downTimeBean10);
        list.add(downTimeBean11);
        list.add(downTimeBean12);
        list.add(downTimeBean13);

        for (int i = 0; i < list.size(); i++) {
            long countTime = timeDifference(list.get(i).getEndTime());
            list.get(i).setCountTime(countTime);
        }

        timeThread = new MyThread(list);
        new Thread(timeThread).start();
        adapter.notifyDataSetChanged();
    }

    public long timeDifference(String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            //活动结束时间转化为Date形式
            Date dead = format.parse(endTime);
            //算出时间差，用ms表示
            diff = dead.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //返回时间差
        return diff;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //刷新适配器
                    adapter.notifyData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        DownTimeBean downTimeBean13 = new DownTimeBean("2017-09-30 24:33:00");
        list.add(downTimeBean13);
        timeThread.setList(list);
        adapter.notifyDataSetChanged();
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
    }

    class MyThread implements Runnable {
        //用来停止线程
        boolean endThread;
        ArrayList<DownTimeBean> list;

        public void setList(ArrayList<DownTimeBean> list) {
            this.list = list;
        }

        public MyThread(ArrayList<DownTimeBean> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (!endThread) {
                try {
                    Thread.sleep(1000);
                    for (int i = 0; i < list.size(); i++) {
                        //拿到每件商品的时间差，转化为具体的多少天多少小时多少分多少秒
                        //并保存在商品time这个属性内
                        long countTime = list.get(i).getCountTime();

                        //如果时间差大于1秒钟，将每件商品的时间差减去一秒钟，
                        // 并保存在每件商品的counttime属性内
                        if (countTime > 1000) {
                            list.get(i).setCountTime(countTime - 1000);
                        }
                    }
                    Message message = new Message();
                    message.what = 1;
                    //发送信息给handler
                    handler.sendMessage(message);
                } catch (Exception e) {

                }
            }
        }
    }

}
