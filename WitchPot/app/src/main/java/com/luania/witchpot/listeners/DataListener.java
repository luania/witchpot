package com.luania.witchpot.listeners;

import android.content.Context;
import android.widget.Toast;

import com.luania.witchpot.R;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogError;

/**
 * Created by luania on 16/6/27.
 */
public abstract class DataListener implements ValueEventListener {

    Context context;

    public DataListener(Context context) {
        this.context = context;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Object object = dataSnapshot.getValue();
        if (object != null) {
            String json = object.toString();
            onJsonGetted(json);
        } else {
            onNoData();
        }
        onFinally();
    }

    public void onNoData() {
        Toast.makeText(context, context.getString(R.string.message_no_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled(WilddogError wilddogError) {
        Toast.makeText(context, wilddogError.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public abstract void onJsonGetted(String json);

    public abstract void onFinally();
}
