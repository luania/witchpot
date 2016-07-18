package com.luania.witchpot.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luania.witchpot.R;
import com.luania.witchpot.activity.LogInActivity;
import com.luania.witchpot.service.UserService;
import com.wilddog.client.AuthData;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by luania on 16/7/4.
 */
public class UserDrawerLayout extends DrawerLayout {

    Context context;

    private TextView tvUserEmail;
    private CircleImageView civProfileImage;
    private Button btnLogout;
    private View navigationView;
    private Button btnHome;

    public UserDrawerLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        navigationView = findViewById(R.id.llNavigation);
        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        civProfileImage = (CircleImageView) findViewById(R.id.civProfileImage);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnHome = (Button) findViewById(R.id.btnHome);

        civProfileImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthData authData = UserService.getAutDATA();
                if (authData != null) {
                    return;
                }
                Intent intent = new Intent(context, LogInActivity.class);
                context.startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.logout();
                Toast.makeText(context, context.getString(R.string.user_logout_success), Toast.LENGTH_SHORT).show();
                setUserData();

                closeDrawer(GravityCompat.START);
            }
        });
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(GravityCompat.START);
            }
        });
        setUserData();
    }

    public void setUserData() {
        if(navigationView == null){
            return;
        }
        AuthData authData = UserService.getAutDATA();
        if (authData != null) {
            tvUserEmail.setText((CharSequence) authData.getProviderData().get("email"));
            btnLogout.setVisibility(VISIBLE);
        } else {
            tvUserEmail.setText(context.getString(R.string.user_email));
            btnLogout.setVisibility(GONE);
        }
    }

    public void close(){
        closeDrawer(navigationView);
    }

    public boolean isOpen(){
        return isDrawerOpen(navigationView);
    }
}
