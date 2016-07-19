package com.luania.witchpot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseFragment;
import com.luania.witchpot.databinding.FragmentSegmentBinding;
import com.luania.witchpot.listeners.OnDrillListener;
import com.luania.witchpot.pojo.SegmentPojo;

/**
 * Created by luania on 16/3/17.
 */
public class SegmentFragment extends BaseFragment {

    private OnDrillListener onDrillListener;
    private FragmentSegmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_segment, container, false);
        binding = FragmentSegmentBinding.bind(rootView);

        onDrillListener = ((CanDrillActivity) getActivity()).getOnDrillListener();
        if (onDrillListener != null) {
            onDrillListener.setUp(binding.autoSwipeRefreshLayout, binding.btnDrillDown, binding.btnDrillUp);
        }
        return rootView;
    }

    public void insertData(SegmentPojo segmentPojo) {
        binding.setSegmentPojo(segmentPojo);
        binding.largenAnimDraweeView.setImage(segmentPojo.decodeImage());
    }

    public void setRefreshing(boolean refreshing) {
        binding.autoSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public interface CanDrillActivity {
        OnDrillListener getOnDrillListener();
    }
}