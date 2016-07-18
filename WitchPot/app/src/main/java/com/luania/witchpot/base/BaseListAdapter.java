package com.luania.witchpot.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by luania on 16/6/28.
 */
public abstract class BaseListAdapter<D> extends BaseAdapter {

    public Context context;
    public List<D> datas;

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        if(datas == null){
            return 0;
        }
        return datas.size();
    }
}
