package com.luania.witchpot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.luania.witchpot.R;
import com.luania.witchpot.adapter.RootAdapter;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivityDrawerBinding;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataParser;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends BaseActivity {

    private List<SegmentPojo> segmentPojos = new ArrayList<>();
    private RootAdapter rootAdapter;
    private long lastBackPressedTime = 0;
    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_drawer);

        initToolbar();
        initRecycler();
        initSwipe();
        initActionBarDrawerToggle();
    }

    private void initToolbar(){
        binding.layoutAppbar.toolbar.setTitle(R.string.other_root_list);
        binding.layoutAppbar.toolbar.inflateMenu(R.menu.create);
        binding.layoutAppbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.itemCreate:
                        if (UserService.checkAuthData(activity)) {
                            CreateSegmentActivity.start(activity, "root");
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void initRecycler() {
        rootAdapter = new RootAdapter(activity, segmentPojos);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recyclerView.setAdapter(rootAdapter);
    }

    private void initSwipe() {
        binding.autoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initActionBarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                DrawerActivity.this, binding.userDrawerLayout, binding.layoutAppbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.userDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadData() {
        DataService.getChildList(activity, "root", new DataListener(activity) {
            @Override
            public void onJsonGetted(String json) {
                segmentPojos.clear();
                segmentPojos.addAll(DataParser.toSegmentList(json));
                rootAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinally() {
                binding.autoSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNoData() {
                super.onNoData();
                segmentPojos.clear();
                rootAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        binding.userDrawerLayout.setUserData();
    }

    @Override
    public void onBackPressed() {
        if (binding.userDrawerLayout.isOpen()) {
            binding.userDrawerLayout.close();
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastBackPressedTime < 2000) {
            super.onBackPressed();
        } else {
            toast(R.string.message_more_press_to_exit);
        }
        lastBackPressedTime = now;
    }
}
