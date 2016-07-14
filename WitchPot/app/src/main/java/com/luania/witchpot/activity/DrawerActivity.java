package com.luania.witchpot.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.luania.witchpot.R;
import com.luania.witchpot.adapter.RootAdapter;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataParser;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.service.UserService;
import com.luania.witchpot.widget.AutoSwipeRefreshLayout;
import com.luania.witchpot.widget.UserDrawerLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrawerActivity extends BaseActivity {

    private UserDrawerLayout userDrawerLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AutoSwipeRefreshLayout autoSwipeRefreshLayout;

    private List<Map.Entry<String, SegmentPojo>> segmentPojos = new ArrayList<>();
    private RootAdapter rootAdapter;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initViews();
    }

    private void initViews() {
        findViews();
        initRecycler();
        initSwipe();
        initToolbar();
        initActionBarDrawerToggle();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userDrawerLayout = (UserDrawerLayout) findViewById(R.id.user_drawer_layout);
        autoSwipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.auto_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
    }

    private void initRecycler() {
        rootAdapter = new RootAdapter(activity, segmentPojos);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(rootAdapter);
    }

    private void initSwipe() {
        autoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.other_root_list);
        toolbar.inflateMenu(R.menu.create);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.item_create:
                        if (UserService.checkAuthData(activity)) {
                            CreateSegmentActivity.start(activity, "root");
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void initActionBarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                DrawerActivity.this, userDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        userDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadData() {
        DataService.getChildList(activity, "root", new DataListener(activity) {
            @Override
            public void onJsonGetted(String json) {
                segmentPojos.clear();
                segmentPojos.addAll(DataParser.toXsList(json));
                rootAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinally() {
                autoSwipeRefreshLayout.setRefreshing(false);
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
        userDrawerLayout.setUserData();
    }

    @Override
    public void onBackPressed() {
        if (userDrawerLayout.isOpen()) {
            userDrawerLayout.close();
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
