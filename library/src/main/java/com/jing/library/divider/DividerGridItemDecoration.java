package com.jing.library.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jing.library.utils.LogUtil;

/**
 * RecyclerView为GridLayoutManager和StaggeredGridLayoutManager时所需添加分割线的工具类
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "DividerGridItem";
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int mOrientation;
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividerGridItemDecoration(Context context, int drawableId) {
        this(context);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        setOrientation(VERTICAL);
    }

    public DividerGridItemDecoration(Context context, int orientation, int drawableId) {
        this(context);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 得到RecyclerView的列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
//            LogUtil.i(TAG, "drawHorizontal" + mDivider.getIntrinsicHeight() + " left:" + left + "right:" + right + "top:" + top + "bottom:" + bottom);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            mDivider.setBounds(0, 0, right, mDivider.getIntrinsicHeight());
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
//            LogUtil.i(TAG, "drawVertical" + mDivider.getIntrinsicWidth() + " left:" + left + "right:" + right + "top:" + top + "bottom:" + bottom);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            mDivider.setBounds(0, 0, mDivider.getIntrinsicWidth(), bottom + mDivider.getIntrinsicHeight());
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int itemPosition = layoutManager.getPosition(view) + 1;
        if (mOrientation == VERTICAL) {
            vertical(outRect, parent, itemPosition);
        } else if (mOrientation == HORIZONTAL) {
            horizontal(outRect, parent, itemPosition);
        }

    }

    private void horizontal(Rect outRect, RecyclerView parent, int itemPosition) {
        //得到总的item个数
        int childCount = parent.getAdapter().getItemCount();
        //得到总的行数
        int rowCount = getSpanCount(parent);
        //得到总的列数
        int columnCount = childCount % rowCount == 0 ? childCount / rowCount : childCount / rowCount + 1;
        //得到item在第几列
        int itemColumn = itemPosition % rowCount == 0 ? itemPosition / rowCount : itemPosition / rowCount + 1;
        //得到item在第几行
        int itemRow = itemPosition % rowCount == 0 ? rowCount : itemPosition % rowCount;
//        LogUtil.i(TAG, "rowCount: " + rowCount + "  columnCount: " + columnCount + "  itemRow: " + itemRow + "  itemColumn: " + itemColumn);
        if (itemRow == 1) {
            //第一行
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, mDivider.getIntrinsicHeight(), 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        } else if (itemRow == rowCount) {
            //最后一行
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        } else {
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        }
    }

    private void vertical(Rect outRect, RecyclerView parent, int itemPosition) {
        //得到总的列数
        int columnCount = getSpanCount(parent);
        //得到总的item个数
        int childCount = parent.getAdapter().getItemCount();
        //得到总的行数
        int rowCount = childCount % columnCount == 0 ? childCount / columnCount : childCount / columnCount + 1;
        //得到item在第几行
        int itemRow = itemPosition % columnCount == 0 ? itemPosition / columnCount : itemPosition / columnCount + 1;
        //得到item在第几列
        int itemColumn = itemPosition % columnCount == 0 ? columnCount : itemPosition % columnCount;
//        LogUtil.i(TAG, "columnCount: " + columnCount + "  rowCount: " + rowCount + "  itemRow: " + itemRow + "  itemColumn: " + itemColumn);
        if (itemRow == 1) {
            //第一行
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        } else if (itemRow == rowCount) {
            //最后一行
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        } else {
            if (itemColumn == 1) {
                //第一列
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else if (itemColumn == columnCount) {
                //最后一列
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        }
    }

}
