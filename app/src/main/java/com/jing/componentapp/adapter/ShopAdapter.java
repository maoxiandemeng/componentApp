package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.proxy.ProxyOnClick;
import com.jing.componentapp.proxy.ProxyParam;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/7/27.
 */

public class ShopAdapter extends BaseRecyclerAdapter<String> {

    public ShopAdapter(Context context, ArrayList<String> mData) {
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

        ItemOnClick defaultOnClick = new ItemOnClick(realPosition);
        holder.tv.setOnClickListener(new ProxyOnClick(defaultOnClick).create());
    }

    public static class ShopHolder extends BaseViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public ShopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemOnClick implements View.OnClickListener, ProxyParam {
        private int i;

        public ItemOnClick(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View view) {
            ToastUtils.showShortToast(i+"");
        }

        @Override
        public String getStr() {
            if (i == 0) return "aa";
            return "123";
        }
    }

}
