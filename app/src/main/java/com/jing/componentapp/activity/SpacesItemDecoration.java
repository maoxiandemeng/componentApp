package com.jing.componentapp.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jing.componentapp.R;

/**
 * Created by liujing on 2017/7/21.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int count = parent.getAdapter().getItemCount();
        if (layoutManager instanceof LinearLayoutManager) {
            if (parent.getChildLayoutPosition(view) != count - 1) {
                //表示不是最后一个item
                outRect.set(space, space, space, 0);
            } else {
                outRect.set(space, space, space, space);
            }
        }
    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        final int left = parent.getPaddingLeft();
//        final int right = parent.getWidth() - parent.getPaddingRight();
//
//        final int childCount = parent.getAdapter().getItemCount();
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof LinearLayoutManager) {
//            for (int i = 0; i < childCount; i++) {
//                final View child = parent.getChildAt(i);
//                RecyclerView v = new RecyclerView(parent.getContext());
//                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                        .getLayoutParams();
//                final int top = child.getBottom() + params.bottomMargin;
//                final int bottom = top + divider.getIntrinsicHeight();
//                Log.i("onDraw", "left: "+left+"top: "+top+"right: "+right+"bottom: "+bottom);
//                divider.setBounds(left, top, right, bottom);
//                divider.draw(c);
//            }
//        }
//    }
}
