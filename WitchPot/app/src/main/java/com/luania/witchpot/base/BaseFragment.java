package com.luania.witchpot.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.luania.witchpot.R;

/**
 * Created by luania on 16/6/25.
 */
public class BaseFragment extends Fragment implements BaseInterface {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void startActivity(Class clas) {
        Intent intent = new Intent(getActivity(), clas);
        startActivity(intent);
    }

    public void startActivityForResult(Class clas, int requestCode) {
        Intent intent = new Intent(getActivity(), clas);
        startActivityForResult(intent, requestCode);
    }

    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    ProgressDialog dialog;

    public void showDialog() {
        dialog = new ProgressDialog(getActivity(), R.style.DialogTheme);
        dialog.setMessage("少女祈祷中。。。");
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
