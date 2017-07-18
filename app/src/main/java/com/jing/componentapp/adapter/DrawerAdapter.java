package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jing.componentapp.R;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by liujing on 2017/7/18.
 */

public class DrawerAdapter extends BaseRecyclerAdapter<String> {

    public DrawerAdapter(Context context, ArrayList<String> mData) {
        super(context, mData);
    }

    @Override
    public int getDefItemViewType(int position) {
        return 0;
    }

    @Override
    protected BaseViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_recycler_item, parent, false);
        return new DrawerHolder(view);
    }

    @Override
    protected void onBind(BaseViewHolder holder, int realPosition, int itemViewType) {

    }

    public class DrawerHolder extends BaseViewHolder{

        public DrawerHolder(View itemView) {
            super(itemView);
        }
    }
}
