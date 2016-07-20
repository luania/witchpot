package com.luania.witchpot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.luania.witchpot.R;
import com.luania.witchpot.base.BaseActivity;
import com.luania.witchpot.databinding.ActivitySignUpBinding;
import com.luania.witchpot.service.UserService;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

import java.util.Map;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_sign_up);

        binding.layoutAppbar.toolbar.setTitle(R.string.user_login);
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = binding.tietEmail.getText().toString().trim();
                final String password = binding.tietPassword.getText().toString().trim();
                final String rePassword = binding.tietRePassword.getText().toString().trim();

                setUp(email, password, rePassword);
            }
        });
    }

    private void setUp(String email, String password, String rePassword) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            toast(getString(R.string.message_input_wrong));
            return;
        } else if (!password.equals(rePassword)) {
            toast(getString(R.string.message_input_password_diff));
            return;
        }

        UserService.createUser(email, password, new Wilddog.ValueResultHandler<Map<String, Object>>() {
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
}
