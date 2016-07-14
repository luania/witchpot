package com.luania.witchpot.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.service.UserService;
import com.wilddog.client.AuthData;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

public class LogInActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private Button btnSignUp;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        findViews();
        toolbar.setTitle(getString(R.string.user_login));

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tietEmail.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    toast(getString(R.string.message_input_wrong));
                }

                UserService.loginWithPassword(email, password, new Wilddog.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        toast(getString(R.string.user_login_successed));
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(WilddogError wilddogError) {
                        toast(wilddogError.getMessage());
                    }
                });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignUpActivity.class);
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tietEmail = (TextInputEditText) findViewById(R.id.tiet_email);
        tietPassword = (TextInputEditText) findViewById(R.id.tiet_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLogIn = (Button) findViewById(R.id.btn_login);
    }

}
