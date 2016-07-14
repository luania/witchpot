package com.luania.witchpot.listeners;

import android.content.Context;
import android.widget.Toast;

import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

/**
 * Created by luania on 16/6/27.
 */
public abstract class DataCompletionListener implements Wilddog.CompletionListener{

    Context context;

    public DataCompletionListener(Context context) {
        this.context = context;
    }

    @Override
    public void onComplete(WilddogError wilddogError, Wilddog wilddog) {
        if(wilddogError == null){
            onSuccess(wilddog);
        }else {
            onError(wilddogError);
        }
        onFinally();
    }

    public void onError(WilddogError wilddogError){
        Toast.makeText(context, wilddogError.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public abstract void onSuccess(Wilddog wilddog);

    public abstract void onFinally();
}
