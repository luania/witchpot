package com.luania.witchpot.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by luania on 16/7/18.
 */
public abstract class BaseRecyclerAdapter<D,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public Context context;
    public List<D> datas;

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }
}
