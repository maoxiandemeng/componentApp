package com.jing.componentapp.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jing.componentapp.R;

/**
 * Created by liujing on 2017/9/15.
 * 自定义首页的倒计时
 */

public class DownTimeView extends FrameLayout {
    //小时转化的毫秒
    final static long hourMs = 1000*60*60;

    //分钟转化的毫秒
    final static long minMs = 1000*60;

    //秒转化的毫秒
    final static long secMs = 1000;

    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;

    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private boolean isRunning = false;
    private View view;

    public DownTimeView(Context context) {
        this(context, null, 0);
    }

    public DownTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        view = View.inflate(context, R.layout.down_time, null);
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        tvMinute = (TextView) view.findViewById(R.id.tv_minute);
        tvSecond = (TextView) view.findViewById(R.id.tv_second);
    }

    public void setTimeBackground(int bgId){
        tvHour.setBackgroundResource(bgId);
        tvMinute.setBackgroundResource(bgId);
        tvSecond.setBackgroundResource(bgId);
    }

    public void setTimeTextColor(int colorId){
        tvHour.setTextColor(getResources().getColor(colorId));
        tvMinute.setTextColor(getResources().getColor(colorId));
        tvSecond.setTextColor(getResources().getColor(colorId));
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (second > 0) {
                second--;
                tvHour.setText(getValue(hour));
                tvMinute.setText(getValue(minute));
                tvSecond.setText(getValue(second));
                handler.sendEmptyMessageDelayed(0, 1000);
            } else if (minute > 0){
                minute--;
                second = 59;
                tvHour.setText(getValue(hour));
                tvMinute.setText(getValue(minute));
                tvSecond.setText(getValue(second));
                handler.sendEmptyMessageDelayed(0, 1000);
            } else if (hour > 0){
                hour--;
                minute = 59;
                second = 59;
                tvHour.setText(getValue(hour));
                tvMinute.setText(getValue(minute));
                tvSecond.setText(getValue(second));
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                isRunning = false;
                tvHour.setText(getValue(0));
                tvMinute.setText(getValue(0));
                tvSecond.setText(getValue(0));
                handler.removeMessages(0);
            }

            return false;
        }
    });

    public boolean isRunning() {
        return isRunning;
    }

    public void start(){
        isRunning = true;
        handler.sendEmptyMessage(0);
    }

    public void stop(){
        isRunning = false;
        tvHour.setText(getValue(0));
        tvMinute.setText(getValue(0));
        tvSecond.setText(getValue(0));
        handler.removeMessages(0);
    }

    private String getValue(int value){
        if (value < 10) {
            return "0"+ value;
        }
        return value+"";
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        stop();
    }

    public void setDownTime(long time){
        setHour(getLimitHour(time));
        setMinute(getLimitMinute(time));
        setSecond(getLimitSecond(time));

        tvHour.setText(getValue(hour));
        tvMinute.setText(getValue(minute));
        tvSecond.setText(getValue(second));

//        start();
    }

    public int getLimitHour(long time){
        int hour = (int) (time / hourMs);
        return hour;
    }

    public int getLimitMinute(long time){
        int hour = getLimitHour(time);
        int minute  = (int) ((time - hour * hourMs) / minMs);
        return minute;
    }

    public int getLimitSecond(long time){
        int hour = getLimitHour(time);
        int minute  = getLimitMinute(time);
        int second = (int) ((time - hour * hourMs - minute * minMs) / secMs);
        return second;
    }
}
