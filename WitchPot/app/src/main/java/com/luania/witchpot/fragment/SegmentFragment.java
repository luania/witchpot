package com.luania.witchpot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseFragment;
import com.luania.witchpot.listeners.OnDrillListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.widget.AutoSwipeRefreshLayout;
import com.luania.witchpot.widget.LargenAnimDraweeView;

/**
 * Created by luania on 16/3/17.
 */
public class SegmentFragment extends BaseFragment {

    private OnDrillListener onDrillListener;

    private AutoSwipeRefreshLayout autoSwipeRefreshLayout;
    private TextView tv_segment;

    private TextView tv_time;
    private Button btn_drill_down;
    private Button btn_drill_up;
    private LargenAnimDraweeView largenAnimDraweeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_segment, container, false);
        initViews(rootView);

        onDrillListener = ((CanDrillActivity) getActivity()).getOnDrillListener();
        if (onDrillListener != null) {
            onDrillListener.setUp(autoSwipeRefreshLayout, btn_drill_down, btn_drill_up);
        }

        return rootView;
    }

    private void initViews(View rootView) {
        autoSwipeRefreshLayout = (AutoSwipeRefreshLayout) rootView.findViewById(R.id.auto_swipe_refresh_layout);

        tv_segment = (TextView) rootView.findViewById(R.id.tv_segment);
        tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        btn_drill_down = (Button) rootView.findViewById(R.id.btn_drill_down);
        btn_drill_up = (Button) rootView.findViewById(R.id.btn_drill_up);
        largenAnimDraweeView = (LargenAnimDraweeView) rootView.findViewById(R.id.largen_anim_drawee_view);
    }

    public void insertData(SegmentPojo segmentPojo) {
        tv_segment.setText(segmentPojo.decodeText());
        tv_time.setText(segmentPojo.createTimeString());
        largenAnimDraweeView.setImageURIWithAnim(segmentPojo.decodeImage());

        if ("root".equals(segmentPojo.getPid())) {
            btn_drill_up.setVisibility(View.INVISIBLE);
        } else {
            btn_drill_up.setVisibility(View.VISIBLE);
        }
    }

    public void setRefreshing(boolean refreshing) {
        autoSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public interface CanDrillActivity {
        OnDrillListener getOnDrillListener();
    }
}