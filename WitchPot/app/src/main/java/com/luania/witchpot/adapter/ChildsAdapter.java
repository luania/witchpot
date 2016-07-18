package com.luania.witchpot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseListAdapter;
import com.luania.witchpot.databinding.ItemChildBinding;
import com.luania.witchpot.pojo.SegmentPojo;

import java.util.List;

/**
 * Created by luania on 16/6/28.
 */
public class ChildsAdapter extends BaseListAdapter<SegmentPojo> {

    public ChildsAdapter(Context context, List<SegmentPojo> segmentPojos) {
        this.context = context;
        this.datas = segmentPojos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.binding.setSegmentPojo(datas.get(position));
        return convertView;
    }

    class ViewHolder {
        ItemChildBinding binding;
        public ViewHolder(View itemView) {
            binding = ItemChildBinding.bind(itemView);
        }
    }
}
