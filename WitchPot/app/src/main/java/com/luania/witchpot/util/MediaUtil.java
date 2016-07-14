package com.luania.witchpot.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.luania.witchpot.base.BaseInterface;

/**
 * Created by luania on 16/7/13.
 */
public class MediaUtil {

    public static final String LITTLE_IMAGE = "-little";

    public static void chooseImage(Activity activity) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {//因为Android SDK在4.4版本后图片action变化了 所以在这里先判断一下
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, BaseInterface.REQUEST_FROM_ADD_SEGMENT_TO_SELECT_IMAGE);
    }

    public static String getLittleImage(String url) {
        return url + "-little";
    }

    public static String getMiniImage(String url) {
        return url + "-mini";
    }

    public static void getBitmapWithFresco(Context context, String url, BaseBitmapDataSubscriber baseBitmapDataSubscriber) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(baseBitmapDataSubscriber,
                CallerThreadExecutor.getInstance());
    }
}
