package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/7/27.
 */

public class RecyclerAutoBottomAdapter extends BaseRecyclerAdapter<String> {

    public RecyclerAutoBottomAdapter(Context context, ArrayList<String> mData) {
        super(context, mData);
    }

    @Override
    public int getDefItemViewType(int position) {
        return 0;
    }

    @Override
    protected BaseViewHolder onCreate(ViewGroup parent, int viewType) {
        return new ShopHolder(LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false));
    }

    @Override
    protected void onBind(BaseViewHolder viewHolder, int realPosition, int itemViewType) {
        ShopHolder holder = (ShopHolder) viewHolder;
        String s = mData.get(realPosition);
        holder.tv.setText(s);
    }

    public static class ShopHolder extends BaseViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public ShopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
