package com.luania.witchpot.listeners;

import android.content.Context;

/**
 * Created by luania on 16/7/4.
 */
public abstract class DataSimpleListener extends DataListener {
    public DataSimpleListener(Context context) {
        super(context);
    }

    @Override
    public void onFinally() {

    }
}
