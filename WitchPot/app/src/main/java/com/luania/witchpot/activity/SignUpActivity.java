package com.luania.witchpot.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.service.DataService;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

import java.util.Map;

public class SignUpActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private TextInputEditText tietRePassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        toolbar.setTitle(getString(R.string.user_login));
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = tietEmail.getText().toString().trim();
                final String password = tietPassword.getText().toString().trim();
                final String rePassword = tietRePassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
                    toast(getString(R.string.message_input_wrong));
                    return;
                } else if (!password.equals(rePassword)) {
                    toast(getString(R.string.message_input_password_diff));
                    return;
                }

                DataService.createUser(email, password, new Wilddog.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        toast(stringObjectMap.toString());
                        finish();
                    }

                    @Override
                    public void onError(WilddogError wilddogError) {
                        toast(wilddogError.getMessage());
                    }
                });
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tietEmail = (TextInputEditText) findViewById(R.id.tiet_email);
        tietPassword = (TextInputEditText) findViewById(R.id.tiet_password);
        tietRePassword = (TextInputEditText) findViewById(R.id.tiet_re_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
    }
}
