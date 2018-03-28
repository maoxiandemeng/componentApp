package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jing.componentapp.R;
import com.jing.componentapp.bean.FuLiBean;
import com.jing.componentapp.widget.ReloadImageView;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/7/19.
 */

public class HomeAdapter extends BaseRecyclerAdapter<FuLiBean> {
    public HomeAdapter(Context context, ArrayList<FuLiBean> mData) {
        super(context, mData);
    }

    @Override
    public int getDefItemViewType(int position) {
        return 0;
    }

    @Override
    protected BaseViewHolder onCreate(ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(context).inflate(R.layout.home_item, parent, false));
    }

    @Override
    protected void onBind(BaseViewHolder viewHolder, int realPosition, int itemViewType) {
        HomeHolder holder = (HomeHolder) viewHolder;
        FuLiBean bean = mData.get(realPosition);
//        ImageLoader loader = new ImageLoader.Builder().url(bean.getUrl()).imgView(holder.img).build();
//        ImageLoaderUtil.getInstance().loadImage(context, loader);
        holder.img.setImageUrl(bean.getUrl());
    }

    public static class HomeHolder extends BaseViewHolder {
        @BindView(R.id.img)
        ReloadImageView img;

        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
