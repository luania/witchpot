package com.luania.witchpot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luania.witchpot.R;
import com.luania.witchpot.activity.SegmentActivity;
import com.luania.witchpot.base.BaseRecyclerAdapter;
import com.luania.witchpot.databinding.ItemRootBinding;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.util.MediaUtil;

import java.util.List;

/**
 * Created by luania on 16/7/4.
 */
public class RootAdapter extends BaseRecyclerAdapter<SegmentPojo,RootAdapter.ViewHolder> {

    public RootAdapter(Context context, List<SegmentPojo> segmentPojos) {
        this.context = context;
        this.datas = segmentPojos;
    }

    @Override
    public RootAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_root,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RootAdapter.ViewHolder holder, final int position) {
        final SegmentPojo segmentPojo = datas.get(position);
        holder.binding.setSegmentPojo(segmentPojo);
        holder.binding.simpleDraweeView.setImageURI(MediaUtil.getLittleImage(segmentPojo.decodeImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SegmentActivity.start(context,segmentPojo.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemRootBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemRootBinding.bind(itemView);
        }
    }
}
