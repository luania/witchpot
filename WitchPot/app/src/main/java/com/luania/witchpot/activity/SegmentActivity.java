package com.luania.witchpot.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.gson.Gson;
import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivitySegmentBinding;
import com.luania.witchpot.fragment.SegmentFragment;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.listeners.OnDrillListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.widget.MenuToolbar;

public class SegmentActivity extends BaseActivity implements SegmentFragment.CanDrillActivity {

    private ActivitySegmentBinding binding;

    public static void start(Context context, String rootId) {
        Intent intent = new Intent(context, SegmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("rootId", rootId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String rootId;
    public SegmentPojo segmentPojo;

    private SegmentFragment segmentFragment;

    private OnDrillListener onDrillListener = new OnDrillListener() {
        @Override
        public void onDrillRefresh() {
            loadData(segmentPojo == null ? rootId : segmentPojo.getId());
        }

        @Override
        public void onDrillDown() {
            ChildListActivity.startForResult(activity, segmentPojo.getId());
        }

        @Override
        public boolean onDrillUp() {
            if (segmentPojo.getPid().equals("root")) {
                return false;
            }
            loadData(segmentPojo.getPid());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(activity, R.layout.activity_segment);
        binding.setToolbarData(new MenuToolbar.ToolbarData(R.string.other_space));

        rootId = getIntent().getExtras().getString("rootId");

        initToolbar();
        initFragment();
    }

    private void initFragment() {
        segmentFragment = new SegmentFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.frameLayoutFragment, segmentFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initToolbar() {
        binding.layoutAppbar.toolbar.setNavigationIcon(R.drawable.navigation_close);
        binding.layoutAppbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FROM_SEGMENT_TO_SELECT_CHILD) {
                String id = data.getStringExtra("id");
                loadData(id);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loadData(String id) {
        DataService.getSegment(activity, id, new DataListener(activity) {
            @Override
            public void onJsonGetted(String json) {
                segmentPojo = new Gson().fromJson(json, SegmentPojo.class);
                segmentFragment.insertData(segmentPojo);
                binding.layoutAppbar.toolbar.setTitle(segmentPojo.decodeTitle());
            }

            @Override
            public void onFinally() {
                segmentFragment.setRefreshing(false);
            }
        });
    }

    @Override
    public OnDrillListener getOnDrillListener() {
        return onDrillListener;
    }

    @Override
    public void onBackPressed() {
        if (onDrillListener.onDrillUp()) {
            return;
        }
        super.onBackPressed();
    }
}
