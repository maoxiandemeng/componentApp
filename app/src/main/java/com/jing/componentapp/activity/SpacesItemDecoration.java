package com.jing.componentapp.activity;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
            Log.i("getItemOffsets","LinearLayoutManager");
            if (parent.getChildLayoutPosition(view) != count - 1) {
                //表示不是最后一个item
                outRect.set(space, space, space, 0);
            } else {
                outRect.set(space, space, space, space);
            }
        } else if (layoutManager instanceof GridLayoutManager){
            Log.i("getItemOffsets","GridLayoutManager: ");
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            Log.i("getItemOffsets","spanCount: "+spanCount);
            int childLayoutPosition = parent.getChildLayoutPosition(view);
            Log.i("getItemOffsets","childLayoutPosition: "+childLayoutPosition);

        }
    }

}
