package com.luania.witchpot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseListAdapter;
import com.luania.witchpot.databinding.ItemChildBinding;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.util.MediaUtil;

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
        final SegmentPojo segmentPojo = datas.get(position);
        holder.binding.setSegmentPojo(segmentPojo);
        holder.binding.simpleDraweeView.setImageURI(MediaUtil.getLittleImage(segmentPojo.decodeImage()));
        return convertView;
    }

    class ViewHolder {
        ItemChildBinding binding;
        public ViewHolder(View itemView) {
            binding = ItemChildBinding.bind(itemView);
        }
    }
}
