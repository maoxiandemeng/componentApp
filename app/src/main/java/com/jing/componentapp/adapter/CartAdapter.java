package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jing.componentapp.R;
import com.jing.componentapp.widget.LogisticsView;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;
import com.jing.library.utils.Helper;
import com.jing.library.utils.StringUtil;
import com.jing.library.utils.TextViewTouchListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/9/6.
 */

public class CartAdapter extends BaseRecyclerAdapter<String> {

    public CartAdapter(Context context, ArrayList<String> mData) {
        super(context, mData);
    }

    @Override
    public int getDefItemViewType(int position) {
        return 0;
    }

    @Override
    protected BaseViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartHolder(view);
    }

    @Override
    protected void onBind(BaseViewHolder viewHolder, int realPosition, int itemViewType) {
        CartHolder holder = (CartHolder) viewHolder;
        if (realPosition == 0) {
            holder.logisticsView.setBitmap(R.drawable.spot_green);
        } else if (realPosition == mData.size() - 1){
            holder.logisticsView.setBitmap(R.drawable.spot, false);
        } else {
            holder.logisticsView.setBitmap(R.drawable.spot);
        }
        String str = mData.get(realPosition);
        String s = Helper.getPhoneByString(str);
        if (StringUtil.isEmpty(s)) {
            holder.tvInfo.setText(str);
        } else {
            holder.tvInfo.setText(Helper.getClickableSpan(context, str, s, R.color.colorAccent));
            holder.tvInfo.setOnTouchListener(new TextViewTouchListener());
        }

    }

    public class CartHolder extends BaseViewHolder {
        @BindView(R.id.logistics_view)
        LogisticsView logisticsView;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
