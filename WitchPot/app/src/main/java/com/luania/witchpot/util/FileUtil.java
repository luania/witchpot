package com.luania.witchpot.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by luania on 16/7/5.
 */
public class FileUtil {

    public static Bitmap getBitmapFromUri(Context context,Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
