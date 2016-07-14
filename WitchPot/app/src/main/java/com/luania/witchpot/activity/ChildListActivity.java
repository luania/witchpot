package com.luania.witchpot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luania.witchpot.R;
import com.luania.witchpot.adapter.ChildAdapter;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataParser;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.widget.AutoSwipeRefreshLayout;

import java.util.List;
import java.util.Map;

public class ChildListActivity extends BaseActivity {

    public static void startForResult(Activity activity, String pid) {
        Intent intent = new Intent(activity, ChildListActivity.class);
        intent.putExtra("pid", pid);
        activity.startActivityForResult(intent, REQUEST_FROM_SEGMENT_TO_SELECT_CHILD);
    }

    private ListView lv_root;
    private Toolbar toolbar;
    private AutoSwipeRefreshLayout autoSwipeRefreshLayout;

    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        pid = getIntent().getStringExtra("pid");

        findViews();
        initToolbar();

        autoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void findViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lv_root = (ListView) findViewById(R.id.lv);
        autoSwipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.auto_swipe_refresh_layout);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.action_select_a_child);
        toolbar.inflateMenu(R.menu.create);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.item_create:
                        CreateSegmentActivity.start(activity, pid);
                        break;
                }
                return false;
            }
        });
    }

    private void loadData() {
        DataService.getChildList(activity, pid, new DataListener(activity) {
            @Override
            public void onJsonGetted(String json) {
                final List<Map.Entry<String, SegmentPojo>> segmentPojos = DataParser.toXsList(json);
                lv_root.setAdapter(new ChildAdapter(activity, segmentPojos));
                lv_root.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("id", segmentPojos.get(position).getKey());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFinally() {
                autoSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }
}