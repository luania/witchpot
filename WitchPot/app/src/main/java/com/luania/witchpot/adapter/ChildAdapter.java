package com.luania.witchpot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luania.witchpot.R;
import com.luania.witchpot.base.SimpleBaseAdapter;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.util.MediaUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by luania on 16/6/28.
 */
public class ChildAdapter extends SimpleBaseAdapter<Map.Entry<String, SegmentPojo>> {

    public ChildAdapter(Context context, List<Map.Entry<String, SegmentPojo>> segmentPojos) {
        this.context = context;
        this.datas = segmentPojos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SegmentPojo segmentPojo = datas.get(position).getValue();
        viewHolder.tvTitle.setText(segmentPojo.decodeText());
        String url = MediaUtil.getMiniImage(segmentPojo.decodeImage());
        viewHolder.simpleDraweeView.setImageURI(url);
        viewHolder.tvAuthor.setText(segmentPojo.getAuthor());
        return convertView;
    }

    class ViewHolder {
        TextView tvAuthor;
        SimpleDraweeView simpleDraweeView;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simple_drawee_view);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
