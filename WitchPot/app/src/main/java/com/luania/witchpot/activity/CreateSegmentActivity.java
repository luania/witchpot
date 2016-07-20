package com.luania.witchpot.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivityCreateSegmentBinding;
import com.luania.witchpot.listeners.DataCompletionListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.service.DataService;
import com.luania.witchpot.service.qiniu.MediaService;
import com.luania.witchpot.util.ColorUtil;
import com.luania.witchpot.util.FileUtil;
import com.luania.witchpot.util.MediaUtil;
import com.luania.witchpot.util.PropertyAnimUtil;
import com.luania.witchpot.widget.HigherAnimDraweeFrameLayout;
import com.wilddog.client.Wilddog;

public class CreateSegmentActivity extends BaseActivity {

    private HigherAnimDraweeFrameLayout higherAnimDraweeFrameLayout;
    private ActivityCreateSegmentBinding binding;

    public static void start(Context context, String pid) {
        Intent intent = new Intent(context, CreateSegmentActivity.class);
        intent.putExtra("pid", pid);
        context.startActivity(intent);
    }

    private String pid;
    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_create_segment);

        pid = getIntent().getStringExtra("pid");

        initToolbar();
        initLargenAnimDraweeFrameLayout();
    }

    private void initToolbar(){
        binding.layoutAppbar.toolbar.setTitle(R.string.action_create_segment);
        binding.layoutAppbar.toolbar.inflateMenu(R.menu.add_segment);
        binding.layoutAppbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.itemConfirm:
                        save();
                        break;
                    case R.id.itemImage:
                        MediaUtil.chooseImage(activity);
                        break;
                }
                return false;
            }
        });
    }

    private void initLargenAnimDraweeFrameLayout(){
        higherAnimDraweeFrameLayout = (HigherAnimDraweeFrameLayout) activity.findViewById(R.id.largenAnimDraweeFrameLayout);
        higherAnimDraweeFrameLayout.setOnHideListener(new PropertyAnimUtil.AnimatorEndListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageUrl = "";
            }
        });
    }

    private void save() {
        String name = binding.etSegmentName.getText().toString();
        String segment = binding.etSegment.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(segment)) {
            toast(R.string.message_input_null_content);
            return;
        }
        showDialog();
        DataService.createSegment(activity, new SegmentPojo(segment, pid, name, imageUrl), new DataCompletionListener(CreateSegmentActivity.this) {
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
        final Uri imageUri = data.getData();
        Bitmap bitmap = FileUtil.getBitmapFromUri(activity, imageUri);
        showDialog();
        MediaService.upLoadImage(bitmap, new MediaService.UpLoadCompletionListener() {
            @Override
            public void upLoadComplete(String url) {
                dismissDialog();
                higherAnimDraweeFrameLayout.setImage(url);
                imageUrl = url;
                MediaUtil.getBitmapWithFresco(activity, url, new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        ColorUtil.tintImageView(bitmap, higherAnimDraweeFrameLayout.getIvRemove());
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                });
            }
        });
    }
}