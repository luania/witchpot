package com.luania.witchpot.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.luania.witchpot.R;
import com.luania.witchpot.adapter.ChildsAdapter;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivityChildListBinding;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataParser;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.widget.MenuToolbar;

import java.util.List;

public class ChildListActivity extends BaseActivity {

    private ActivityChildListBinding binding;

    public static void startForResult(Activity activity, String pid) {
        Intent intent = new Intent(activity, ChildListActivity.class);
        intent.putExtra("pid", pid);
        activity.startActivityForResult(intent, REQUEST_FROM_SEGMENT_TO_SELECT_CHILD);
    }

    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_child_list);

        binding.setToolbarData(
                new MenuToolbar.ToolbarData(
                        R.string.action_select_a_child, R.menu.create, new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.itemCreate:
                                CreateSegmentActivity.start(activity, pid);
                                break;
                        }
                        return false;
                    }
                }));

        pid = getIntent().getStringExtra("pid");
        binding.autoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        DataService.getChildList(activity, pid, new DataListener(activity) {
            @Override
            public void onJsonGetted(String json) {
                final List<SegmentPojo> segmentPojos = DataParser.toSegmentList(json);
                binding.listView.setAdapter(new ChildsAdapter(activity, segmentPojos));
                binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("id", segmentPojos.get(position).getId());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFinally() {
                binding.autoSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }
}