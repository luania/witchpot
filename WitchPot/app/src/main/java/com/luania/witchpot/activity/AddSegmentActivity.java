package com.luania.witchpot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.listeners.DataCompletionListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.service.qiniu.MediaService;
import com.luania.witchpot.util.FileUtil;
import com.luania.witchpot.util.MediaUtil;
import com.luania.witchpot.widget.LargenAnimDraweeFrameLayout;
import com.wilddog.client.AuthData;
import com.wilddog.client.Wilddog;

public class AddSegmentActivity extends BaseActivity {

    private LargenAnimDraweeFrameLayout largenAnimDraweeFrameLayout;

    public static void start(Context context, String pid) {
        Intent intent = new Intent(context, AddSegmentActivity.class);
        intent.putExtra("pid", pid);
        context.startActivity(intent);
    }

    private String pid;

    private Toolbar toolbar;
    private EditText etXsName;
    private EditText etXsStart;

    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_segment);

        pid = getIntent().getStringExtra("pid");

        findViews();
        initToolbar();

        largenAnimDraweeFrameLayout = (LargenAnimDraweeFrameLayout) activity.findViewById(R.id.largen_anim_drawee_frame_layout);
        largenAnimDraweeFrameLayout.setOnHideListener(new LargenAnimDraweeFrameLayout.OnHideListener() {
            @Override
            public void onHide() {
                imageUrl = "";
            }
        });
    }

    private void findViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etXsName = (EditText) findViewById(R.id.et_segment_name);
        etXsStart = (EditText) findViewById(R.id.et_segment_start);
    }

    private void initToolbar(){
        toolbar.setTitle(getString(R.string.action_create_segment));
        toolbar.inflateMenu(R.menu.add_segment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.item_confirm:
                        save();
                        break;
                    case R.id.item_image:
                        MediaUtil.chooseImage(activity);
                        break;
                }
                return false;
            }
        });
    }

    private void save() {
        String name = etXsName.getText().toString();
        String start = etXsStart.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(start)) {
            toast(R.string.message_input_null_content);
            return;
        }

        showDialog();
        DataService.createSegment(activity, new SegmentPojo(start, pid, name, imageUrl), new DataCompletionListener(AddSegmentActivity.this) {
            @Override
            public void onSuccess(Wilddog wilddog) {
                toast(R.string.action_create_success);
                finish();
            }

            @Override
            public void onFinally() {
                dismissDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_FROM_ADD_SEGMENT_TO_SELECT_IMAGE) {
            upLoadImage(data);
        }
    }

    private void upLoadImage(Intent data) {
        AuthData authData = DataService.getAutDATA();
        if (authData == null) {
            Toast.makeText(activity, R.string.user_please_login, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        final Uri imageUri = data.getData();
        Bitmap bitmap = FileUtil.getBitmapFromUri(activity, imageUri);
        showDialog();
        MediaService.upLoadImage(bitmap, activity, new MediaService.UpLoadCompletionListener() {
            @Override
            public void upLoadComplete(String url) {
                dismissDialog();
                largenAnimDraweeFrameLayout.setImageURI(url);
                imageUrl = url;
            }
        });
    }
}