package com.jing.componentapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jing.componentapp.R;
import com.jing.componentapp.bean.DownTimeBean;
import com.jing.componentapp.widget.DownTimeView;
import com.jing.library.adapter.BaseRecyclerAdapter;
import com.jing.library.adapter.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/9/30.
 */

public class ListDownTimeAdapter extends BaseRecyclerAdapter<DownTimeBean> {
    public ArrayList<DownTimeHolder> myViewHolderList = new ArrayList<>();

    public ListDownTimeAdapter(Context context, ArrayList<DownTimeBean> mData) {
        super(context, mData);
    }

    @Override
    public int getDefItemViewType(int position) {
        return 0;
    }

    @Override
    protected BaseViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_down_time_item, parent, false);
        return new DownTimeHolder(view);
    }

    @Override
    protected void onBind(BaseViewHolder viewHolder, int realPosition, int itemViewType) {
        DownTimeHolder holder = (DownTimeHolder) viewHolder;
        holder.setDataPosition(realPosition);
        if (!myViewHolderList.contains(holder)){
            myViewHolderList.add(holder);
        }
        DownTimeBean bean = mData.get(realPosition);
        holder.downTimeView.setDownTime(bean.getCountTime());
    }

    //遍历list，刷新相应holder的TextView
    public void notifyData(){
        for(int i = 0;i < myViewHolderList.size(); i++){
            myViewHolderList.get(i).downTimeView
                    .setDownTime(mData.get(myViewHolderList.get(i).position).getCountTime());
        }
    }

    class DownTimeHolder extends BaseViewHolder {
        @BindView(R.id.down_time)
        DownTimeView downTimeView;
        private int position;

        private void setDataPosition(int position){
            this.position = position;
        }

        public DownTimeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
