package com.luania.witchpot.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.luania.witchpot.R;

/**
 * Created by luania on 16/6/24.
 */
public class BaseActivity extends AppCompatActivity implements BaseInterface {

    public BaseActivity activity;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    public void startActivity(Class clas) {
        Intent intent = new Intent(activity, clas);
        startActivity(intent);
    }

    public void toast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

    public void showDialog() {
        dialog = new ProgressDialog(activity, R.style.DialogTheme);
        dialog.setMessage(getString(R.string.message_shoujo_inori));
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
