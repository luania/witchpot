package com.luania.witchpot.activity;

import android.os.Bundle;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoaddingAvtivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadding);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(DrawerActivity.class);
                finish();
            }
        };

        new Timer(true).schedule(task,1000);
    }
}
