package com.luania.witchpot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luania.witchpot.R;
import com.luania.witchpot.activity.SegmentActivity;
import com.luania.witchpot.pojo.SegmentPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by luania on 16/7/4.
 */
public class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {

    private Context context;
    private List<Map.Entry<String, SegmentPojo>> segmentPojos;

    public RootAdapter(Context context, List<Map.Entry<String, SegmentPojo>> segmentPojos) {
        this.context = context;
        this.segmentPojos = segmentPojos;
    }

    @Override
    public RootAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_root,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RootAdapter.ViewHolder holder, final int position) {
        final Map.Entry<String, SegmentPojo> segmentPojoEntry = segmentPojos.get(position);
        holder.tvTitle.setText(segmentPojoEntry.getValue().decodeTitle());
        String url = segmentPojoEntry.getValue().decodeImage()+"-little";
        holder.simpleDraweeView.setImageURI(url);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SegmentActivity.start(context,segmentPojoEntry.getValue().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(segmentPojos == null){
            return 0;
        }
        return segmentPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        SimpleDraweeView simpleDraweeView;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simple_drawee_view);
        }
    }
}
