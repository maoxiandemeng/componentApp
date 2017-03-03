package com.jing.library.adapter.listener;


import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;

/**
 */

public interface OnRecyclerItemClickListener {
    void setOnRecyclerItemClick(BaseViewHolder holder, BaseRecyclerAdapter adapter, int position);
}
