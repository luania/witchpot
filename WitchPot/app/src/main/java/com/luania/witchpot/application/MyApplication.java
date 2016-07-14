package com.luania.witchpot.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wilddog.client.Wilddog;

/**
 * Created by luania on 16/6/24.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Wilddog.setAndroidContext(this);
    }
}
