package com.jing.library.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import com.jing.library.adapter.animation.AlphaInAnimation;
import com.jing.library.adapter.animation.BaseAnimation;
import com.jing.library.adapter.animation.ScaleInAnimation;
import com.jing.library.adapter.animation.SlideInBottomAnimation;
import com.jing.library.adapter.animation.SlideInLeftAnimation;
import com.jing.library.adapter.animation.SlideInRightAnimation;
import com.jing.library.adapter.listener.OnRecyclerItemClickListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by jon on 2016/12/27
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_EMPTY = 2;

    protected Context context;
    protected LayoutInflater inflater;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    private View mHeaderView;
    private View mEmptyView;
    protected ArrayList<T> mData = new ArrayList<>();

    private boolean mFirstOnlyEnable = true;
    private boolean mOpenAnimationEnable = false;
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public static final int ALPHAIN = 0x00000001;
    public static final int SCALEIN = 0x00000002;
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    public static final int SLIDEIN_LEFT = 0x00000004;
    public static final int SLIDEIN_RIGHT = 0x00000005;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public void setmEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public ArrayList<T> getmData() {
        return mData;
    }

    protected BaseRecyclerAdapter(Context context, ArrayList<T> mData) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        if (mData != null) {
            this.mData = mData;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }

        if (mData.size() == 0) {
            if (position == 0 && mHeaderView == null) {
                return TYPE_EMPTY;
            } else if (position == 1 && mEmptyView != null) {
                return TYPE_EMPTY;
            }
        }

        return getDefItemViewType(position - getHeaderLayoutCount());
    }

    public abstract int getDefItemViewType(int position);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView);
        } else if (mEmptyView != null && viewType == TYPE_EMPTY) {
            return new BaseViewHolder(mEmptyView);
        }
        return onCreate(parent, viewType);
    }

    protected abstract BaseViewHolder onCreate(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == TYPE_HEADER) return;
        if (itemViewType == TYPE_EMPTY) return;

        final int realPosition = getRealPosition(holder);
        onBind(holder, realPosition, itemViewType);

        if (onRecyclerItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerItemClickListener.setOnRecyclerItemClick(holder, BaseRecyclerAdapter.this, realPosition);
                }
            });
        }
    }

    protected abstract void onBind(BaseViewHolder viewHolder, int realPosition, int itemViewType);

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (mHeaderView == null) {
            if (mEmptyView != null && size == 0) {
                return 1;
            } else if (size > 0){
                return size;
            }
        } else if (mHeaderView != null) {
            if (mEmptyView != null && size == 0) {
                return getHeaderLayoutCount()+1;
            } else if (size > 0){
                return size + getHeaderLayoutCount();
            }
        }
        return size;
    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getItemViewType(position);
                    if (mSpanSizeLookup == null) {
                        return itemViewType == TYPE_HEADER || itemViewType == TYPE_EMPTY ? gridLayoutManager.getSpanCount() : 1;
                    } else {
                        return itemViewType == TYPE_HEADER || itemViewType == TYPE_EMPTY ? gridLayoutManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridLayoutManager, position - getHeaderLayoutCount());
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int itemViewType = holder.getItemViewType();
        if (itemViewType == TYPE_HEADER || itemViewType == TYPE_EMPTY) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        } else {
            addAnimation(holder);
        }
    }

    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    private int getRealPosition(BaseViewHolder holder){
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public void addData(T data){
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
    }

    public void addData(int position, T data){
        if (0 <= position && position < mData.size()) {
            mData.add(position, data);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mData.size() - position);
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void addData(int position, ArrayList<T> data) {
        if (0 <= position && position < mData.size()) {
            mData.addAll(position, data);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mData.size() - position - data.size());
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void addData(ArrayList<T> newData) {
        if (!newData.isEmpty()) {
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void clearAddData(ArrayList<T> newData) {
        mData.clear();
        if (!newData.isEmpty()) {
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

//    public void remove(int position){
//        mData.remove(position);
//        notifyItemRemoved(position + getHeaderLayoutCount());
//    }

    public void remove(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }

    public void remove(T data){
        mData.remove(data);
        notifyDataSetChanged();
    }

    public void removeAll(){
        mData.clear();
        notifyDataSetChanged();
    }

    public int getHeaderLayoutCount() {
        return mHeaderView == null ? 0 : 1;
    }

    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }
}
