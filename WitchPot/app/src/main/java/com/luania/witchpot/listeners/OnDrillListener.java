package com.luania.witchpot.listeners;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public abstract class OnDrillListener implements Serializable {
    public abstract void onDrillRefresh();

    public abstract void onDrillDown();

    public abstract boolean onDrillUp();

    public void setUp(final SwipeRefreshLayout swipeRefreshLayout, Button btn_drill_down, Button btn_drill_up) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDrillRefresh();
            }
        });
        btn_drill_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrillDown();
            }
        });

        btn_drill_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrillUp();
            }
        });
    }
}