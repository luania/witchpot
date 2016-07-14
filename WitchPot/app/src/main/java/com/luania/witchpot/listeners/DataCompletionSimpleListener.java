package com.luania.witchpot.listeners;

import android.content.Context;

/**
 * Created by luania on 16/7/4.
 */
public abstract class DataCompletionSimpleListener extends DataCompletionListener {

    public DataCompletionSimpleListener(Context context) {
        super(context);
    }

    @Override
    public void onFinally() {

    }
}
