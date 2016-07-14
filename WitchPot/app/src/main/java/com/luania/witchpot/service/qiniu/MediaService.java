package com.luania.witchpot.service.qiniu;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

import com.luania.witchpot.service.UserService;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by luania on 16/7/5.
 */
public class MediaService {
    public static final String ACCESS_KEY = "URBNzAOftewEmVhZQa12kte8pqmhDuAw0-5QnRAM";
    public static final String SECRET_KEY = "PVO00bEtLPSrKQqj6Y6uvQVRpo1zCdVvhycfcZwL";
    public static final String BUCKET_NAME = "witchpot";
    public static final String URL = "http://o9se8xpch.bkt.clouddn.com/";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void upLoadImage(Bitmap bitmap, final UpLoadCompletionListener upLoadCompletionListener) {
        UploadManager uploadManager = new UploadManager();

        String uid = UserService.getAutDATA().getUid();
        String key = uid + System.currentTimeMillis() + ".png";

        final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(BUCKET_NAME);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        uploadManager.put(baos.toByteArray(), key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        try {
                            String downloadRUL = URL + res.getString("key");
                            upLoadCompletionListener.upLoadComplete(downloadRUL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
    }

    public interface UpLoadCompletionListener {
        void upLoadComplete(String url);
    }

}
