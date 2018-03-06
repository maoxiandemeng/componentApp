package com.jing.library.tab;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by liujing on 2018/2/8.
 */

public abstract class TabAdapter<T> {

    private ArrayList<T> data;
    private OnDataChangedListener onDataChangedListener;

    public TabAdapter(ArrayList<T> data) {
        this.data = data;
    }

    public TabAdapter(T[] data) {
        this.data = new ArrayList<>(Arrays.asList(data));
    }

    public abstract View getView(CommonTabView parent, T t, int position);

    public T getItem(int position){
        return data.get(position);
    }

    public int getCount(){
        return data == null ? 0 : data.size();
    }

    public void notifyDataChanged() {
        if (onDataChangedListener != null)
            onDataChangedListener.onChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    interface OnDataChangedListener {
        void onChanged();
    }
}
