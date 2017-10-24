package com.jing.library.flow;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujing on 2017/9/20.
 */

public class FlowLayoutManager extends RecyclerView.LayoutManager {
    private SparseArray<Rect> mRects;
    //竖直偏移量 每次换行时，要根据这个offset判断
    private int mVerticalOffset;

    public FlowLayoutManager() {
        mRects = new SparseArray<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //布局的具体实现
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (getItemCount() <= 0 || state.isPreLayout()) return;

        //将界面上的所有item都detach掉, 并缓存,以便下次直接拿出来显示
        detachAndScrapAttachedViews(recycler);

        mVerticalOffset = 0;
        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect();
            //根据position得到View
            View view = recycler.getViewForPosition(i);
            //测量View的宽高
            measureChildWithMargins(view, 0, 0);
            //得到宽
            int width = getDecoratedMeasuredWidth(view);
            //得到高
            int height = getDecoratedMeasuredHeight(view);


        }

    }
}
