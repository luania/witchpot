package com.luania.witchpot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivityLogInBinding;
import com.luania.witchpot.service.UserService;
import com.luania.witchpot.widget.MenuToolbar;
import com.wilddog.client.AuthData;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

public class LogInActivity extends BaseActivity {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_log_in);
        binding.setToolbarData(new MenuToolbar.ToolbarData(R.string.user_login,0,null));
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.tietEmail.getText().toString().trim();
                String password = binding.tietPassword.getText().toString().trim();

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
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignUpActivity.class);
            }
        });
    }
}
