package com.jing.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View convertView;

    private final SparseArray<View> views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
    }

    public View getConvertView() {
        return convertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
