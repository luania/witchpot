package com.luania.witchpot.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.luania.witchpot.R;

/**
 * Created by luania on 16/7/13.
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout {
    private OnRefreshListener onRefreshListener;
    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        post(new Runnable() {
            @Override
            public void run() {
                setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
        onRefreshListener = listener;
    }
}
