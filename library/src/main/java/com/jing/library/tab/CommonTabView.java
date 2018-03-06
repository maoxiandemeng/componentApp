package com.jing.library.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by liujing on 2018/2/8.
 */

public class CommonTabView extends HorizontalScrollView implements TabAdapter.OnDataChangedListener {
    private Context context;
    private LinearLayout container;

    private OnTabSelectListener onTabSelectListener;
    private TabAdapter tabAdapter;
    private int screenWidth;

    public CommonTabView(Context context) {
        this(context, null, 0);
    }

    public CommonTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        container = new LinearLayout(context);
        addView(container);
        screenWidth = getScreenWidth();
        init();
    }

    private void init() {

    }

    public void setAdapter(TabAdapter adapter){
        tabAdapter = adapter;
        tabAdapter.setOnDataChangedListener(this);
        changeAdapter();
    }

    private void changeAdapter() {
        if (container != null && container.getChildCount() > 0) {
            container.removeAllViews();
        }
        int count = tabAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = tabAdapter.getView(this, tabAdapter.getItem(i), i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/count, LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            container.addView(view);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTabSelectListener != null) {
                        onTabSelectListener.onTabSelect(position);
                    }
                }
            });
        }
    }


    @Override
    public void onChanged() {
        changeAdapter();
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener{
        void onTabSelect(int tab);
    }

    /**
     * 获取屏幕的宽度（单位：px）

     */
    public int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
