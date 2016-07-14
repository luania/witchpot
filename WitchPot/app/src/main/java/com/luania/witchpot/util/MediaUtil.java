package com.luania.witchpot.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.luania.witchpot.base.BaseInterface;

/**
 * Created by luania on 16/7/13.
 */
public class MediaUtil {

    public static void chooseImage(Activity activity){
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
}
